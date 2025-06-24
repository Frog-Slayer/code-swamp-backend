package dev.codeswamp.articlecommand.application.usecase.command.article.draft

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.application.rebase.SnapshotPolicy
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.CreatePublishCommand
import dev.codeswamp.articlecommand.application.usecase.command.article.publish.PublishArticleResult
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.articlecommand.domain.article.service.SlugUniquenessChecker
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
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val contentReconstructor: ArticleContentReconstructor,
    private val diffProcessor: DiffProcessor,
    private val snapshotPolicy: SnapshotPolicy,
    private val eventRecorder: EventRecorder
) : DraftArticleUseCase {

    @Transactional
    override suspend fun create(command: CreateDraftCommand): DraftArticleResult {
        val fullContent = contentReconstructor.contentFromInitialDiff(command.diff)

        val article = command.toCreateArticleCommand(fullContent)
            .let(VersionedArticle::create)
            .draft()
            .also { articleRepository.create(it) }

        eventRecorder.recordAll(article.pullEvents())

        return DraftArticleResult(
            article.id,
            article.currentVersion.id
        )
    }

    private fun CreateDraftCommand.toCreateArticleCommand(fullContent: String) = CreateArticleCommand(
        generateId = idGenerator::generateId,
        authorId = userId,
        createdAt = Instant.now(),
        metadata = createDefaultMetadata(folderId),
        title = title,
        diff = diff,
        fullContent = fullContent,
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
        val hasMeaningfulDiff = diffProcessor.hasValidDelta(command.diff)

        val existingArticle = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val fullContent = contentReconstructor.reconstructFullContent(existingArticle.currentVersion)
                            .let { contentReconstructor.applyDiff(it, command.diff) }

        val snapshotContent = if ( snapshotPolicy.shouldSaveSnapshot(existingArticle)) fullContent else null

        val articleAfterUpdate = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateVersionIfChanged(command.toArticleVersionUpdateCommand(hasMeaningfulDiff))
            .withSnapshot(snapshotContent)
            .draft()
            .also { articleRepository.update(it) }

        if (existingArticle != articleAfterUpdate) {
            eventRecorder.recordAll(articleAfterUpdate.pullEvents())
        }

        return DraftArticleResult(
             articleAfterUpdate.id,
            articleAfterUpdate.currentVersion.id
        )
    }

    private fun UpdateDraftCommand.toArticleVersionUpdateCommand(hasMeaningfulDiff : Boolean) = ArticleVersionUpdateCommand(
        hasMeaningfulDiff = hasMeaningfulDiff,
        generateId = idGenerator::generateId,
        createdAt = Instant.now(),
        title = title,
        diff = diff,
    )
}