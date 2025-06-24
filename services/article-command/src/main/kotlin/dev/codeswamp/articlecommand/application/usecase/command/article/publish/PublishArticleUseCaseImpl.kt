package dev.codeswamp.articlecommand.application.usecase.command.article.publish

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.application.rebase.SnapshotPolicy
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.command.ArticleVersionUpdateCommand
import dev.codeswamp.articlecommand.domain.article.model.command.CreateArticleCommand
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.articlecommand.domain.article.service.ArticleVersionTransitionSideEffectHandler
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import dev.codeswamp.core.application.event.EventRecorder
import dev.codeswamp.core.domain.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PublishArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val diffProcessor: DiffProcessor,
    private val contentReconstructor: ArticleContentReconstructor,
    private val versionTransitionSideEffectHandler: ArticleVersionTransitionSideEffectHandler,
    private val snapshotPolicy: SnapshotPolicy,
    private val eventRecorder: EventRecorder,
) : PublishArticleUseCase {

    @Transactional
    override suspend fun create(command: CreatePublishCommand): PublishArticleResult {
        val fullContent = contentReconstructor.contentFromInitialDiff(command.diff)

        val article = command.toCreateArticleCommand(fullContent)
            .let(VersionedArticle::create)
            .publish(fullContent)
            .also { articleRepository.create(it) }

        eventRecorder.recordAll(article.pullEvents())

        return PublishArticleResult( article.id, article.currentVersion.id )
    }

    private fun CreatePublishCommand.toCreateArticleCommand(fullContent: String) = CreateArticleCommand(
        generateId = idGenerator::generateId,
        authorId = userId,
        createdAt = Instant.now(),
        metadata = extractMetadata(),
        title = title,
        diff = diff,
        fullContent = fullContent,
    )
 private fun CreatePublishCommand.extractMetadata() =  ArticleMetadata( folderId = folderId,
        summary = summary,
        thumbnailUrl = thumbnailUrl,
        isPublic = isPublic,
        slug = Slug.Companion.of(slug)
    )


    @Transactional
    override suspend fun update(command: UpdatePublishCommand): PublishArticleResult {
        val existingArticle = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val hasMeaningfulDiff = diffProcessor.hasValidDelta(command.diff)
        val fullContent = contentReconstructor.reconstructFullContent(existingArticle.currentVersion)
                            .let { contentReconstructor.applyDiff(it, command.diff) }

        val shouldSaveAsSnapshot = snapshotPolicy.shouldSaveAsSnapshot( existingArticle )
        val snapshotContent = if (shouldSaveAsSnapshot) fullContent else null

        val articleAfterUpdate = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateMetadata(command.extractMetadata() )
            .updateVersionIfChanged(command.toArticleVersionUpdateCommand(hasMeaningfulDiff))
            .withSnapshot(snapshotContent)
            .publish(fullContent)
            .also {
                versionTransitionSideEffectHandler.handlePublishSideEffect(existingArticle, it)
            }


        if (existingArticle != articleAfterUpdate) {//메타데이터 변화, 버전 업데이트, 버전 상태 전이가 있는 경우에만 DB 반영 & 이벤트 발행
            articleRepository.update(articleAfterUpdate)
            eventRecorder.recordAll(articleAfterUpdate.pullEvents())
        }

        return PublishArticleResult(
            articleAfterUpdate.id,
            articleAfterUpdate.currentVersion.id
        )
    }

    private fun UpdatePublishCommand.toArticleVersionUpdateCommand(hasMeaningfulDiff : Boolean) = ArticleVersionUpdateCommand(
        hasMeaningfulDiff = hasMeaningfulDiff,
        generateId = idGenerator::generateId,
        createdAt = Instant.now(),
        title = title,
        diff = diff,
    )

    private fun UpdatePublishCommand.extractMetadata() =  ArticleMetadata(
        folderId = folderId,
        summary = summary,
        thumbnailUrl = thumbnailUrl,
        isPublic = isPublic,
        slug = Slug.Companion.of(slug)
    )
}