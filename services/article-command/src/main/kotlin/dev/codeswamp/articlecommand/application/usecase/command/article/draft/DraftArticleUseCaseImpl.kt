package dev.codeswamp.articlecommand.application.usecase.command.article.draft

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.domain.article.model.Article
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import dev.codeswamp.core.application.event.EventRecorder
import dev.codeswamp.core.domain.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class DraftArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val diffProcessor: DiffProcessor,
    private val eventRecorder: EventRecorder,
) : DraftArticleUseCase {

    @Transactional
    override suspend fun create(command: CreateDraftCommand): DraftArticleResult {
        val (article, version) = command.toCreateArticleCommand()
            .let(Article::create)

        val drafted = article.draft(version.id)
                            .also {
                                articleRepository.createMetadata(it)
                                articleRepository.insertVersions(listOf(it.getVersion(version.id)))
                            }

        eventRecorder.recordAll(drafted.pullEvents())

        return DraftArticleResult(
            article.id,
            version.id
        )
    }

    private fun CreateDraftCommand.toCreateArticleCommand() = CreateArticleCommand(
        generateId = idGenerator::generateId,
        authorId = userId,
        createdAt = Instant.now(),
        metadata = createDefaultMetadata(folderId),
        title = title,
        diff = diff,
    )

    private fun createDefaultMetadata(folderId: Long) = ArticleMetadata(
        folderId = folderId,
        summary = "",
        thumbnailUrl = null,
        isPublic = false,
        slug = null,
    )

    @Transactional
    override suspend fun update(command: UpdateDraftCommand): DraftArticleResult {
        val existingArticle = articleRepository.findById(command.articleId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val (mayUpdated, version) = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateVersionIfChanged(command.toArticleVersionUpdateCommand(diffProcessor))

        val drafted = mayUpdated.draft(version.id)

        val (isMetadataChanged, versionDiff) = drafted.diff(existingArticle)

        if (isMetadataChanged || versionDiff.addedVersions.isNotEmpty() || versionDiff.changedVersions.isNotEmpty()) {
            if (isMetadataChanged) articleRepository.updateMetadata(drafted)

            // DB 제약 사항으로 인해 update -> insert의 순서가 중요함
            if (versionDiff.changedVersions.isNotEmpty()) articleRepository.updateVersions(versionDiff.changedVersions)
            if (versionDiff.addedVersions.isNotEmpty()) articleRepository.insertVersions(versionDiff.addedVersions)
            eventRecorder.recordAll(drafted.pullEvents())
        }

        return DraftArticleResult(
             drafted.id,
            version.id
        )
    }

    private fun UpdateDraftCommand.toArticleVersionUpdateCommand(diffProcessor: DiffProcessor) = ArticleVersionUpdateCommand(
        diffProcessor = diffProcessor,
        generateId = idGenerator::generateId,
        createdAt = Instant.now(),
        title = title,
        diff = diff,
        currentVersionId = versionId,
    )
}