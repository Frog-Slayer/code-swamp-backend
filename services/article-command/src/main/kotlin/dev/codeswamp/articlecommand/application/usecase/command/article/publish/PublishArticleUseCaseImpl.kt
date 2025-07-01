package dev.codeswamp.articlecommand.application.usecase.command.article.publish

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.model.Article
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import dev.codeswamp.core.domain.IdGenerator
import dev.codeswamp.framework.application.outbox.EventRecorder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PublishArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val diffProcessor: DiffProcessor,
    private val eventRecorder: EventRecorder,
) : PublishArticleUseCase {

    @Transactional
    override suspend fun create(command: CreatePublishCommand): PublishArticleResult {
        val (article, version) = command.toCreateArticleCommand()
            .let(Article::create)

        val fullContent = article.restoreFullContent(version.id, diffProcessor)
        val published = article.publish( version.id, fullContent)
            .also {
                articleRepository.createMetadata(it)
                articleRepository.insertVersions(listOf(it.getVersion(version.id)))
            }

        eventRecorder.recordAll( published.pullEvents())

        return PublishArticleResult(
            article.id,
            version.id
        )
    }

    private fun CreatePublishCommand.toCreateArticleCommand() = CreateArticleCommand(
        generateId = idGenerator::generateId,
        authorId = userId,
        createdAt = Instant.now(),
        metadata = extractMetadata(),
        title = title,
        diff = diff,
    )
 private fun CreatePublishCommand.extractMetadata() =  ArticleMetadata( folderId = folderId,
        summary = summary,
        thumbnailUrl = thumbnailUrl,
        isPublic = isPublic,
        slug = Slug.Companion.of(slug)
    )


    @Transactional
    override suspend fun update(command: UpdatePublishCommand): PublishArticleResult {
        val existingArticle = articleRepository.findById(command.articleId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val (mayUpdated, version) = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateMetadata(command.extractMetadata())
            .updateVersionIfChanged(command.toArticleVersionUpdateCommand(diffProcessor))

        val fullContent = mayUpdated.restoreFullContent(version.id, diffProcessor)
        val published = mayUpdated.publish(version.id, fullContent)

        val (isMetadataChanged, versionDiff) = published.diff(existingArticle)

        if (isMetadataChanged || versionDiff.addedVersions.isNotEmpty() || versionDiff.changedVersions.isNotEmpty()) {
            if (isMetadataChanged) articleRepository.updateMetadata(published)

            // DB 제약 사항으로 인해 update -> insert의 순서가 중요함
            if (versionDiff.changedVersions.isNotEmpty()) articleRepository.updateVersions(versionDiff.changedVersions)
            if (versionDiff.addedVersions.isNotEmpty()) articleRepository.insertVersions(versionDiff.addedVersions)
            eventRecorder.recordAll(published.pullEvents())
        }

        return PublishArticleResult(
            published.id,
            version.id
        )
    }

    private fun UpdatePublishCommand.toArticleVersionUpdateCommand(diffProcessor: DiffProcessor) = ArticleVersionUpdateCommand(
        currentVersionId = versionId,
        title = title,
        diff = diff,
        diffProcessor = diffProcessor,
        generateId = idGenerator::generateId,
        createdAt = Instant.now(),
    )

    private fun UpdatePublishCommand.extractMetadata() =  ArticleMetadata(
        folderId = folderId,
        summary = summary,
        thumbnailUrl = thumbnailUrl,
        isPublic = isPublic,
        slug = Slug.Companion.of(slug)
    )
}