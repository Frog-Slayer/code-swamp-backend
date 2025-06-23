package dev.codeswamp.articlecommand.application.usecase.command.article.publish

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.application.rebase.RebasePolicy
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.model.vo.Slug
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.articlecommand.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
import dev.codeswamp.core.application.event.EventRecorder
import dev.codeswamp.core.application.event.outbox.OutboxEventRepository
import dev.codeswamp.core.domain.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PublishArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val diffProcessor: DiffProcessor,
    private val contentReconstructor: ArticleContentReconstructor,
    private val rebasePolicy: RebasePolicy,
    private val eventRecorder: EventRecorder,
) : PublishArticleUseCase {

    @Transactional
    override suspend fun create(command: CreatePublishCommand): PublishArticleResult {
        val createdAt = Instant.now()

        val article = VersionedArticle.Companion.create(
            authorId = command.userId,
            id = idGenerator.generateId(),
            createdAt = createdAt,
            metadata = ArticleMetadata(
                folderId = command.folderId,
                summary = command.summary,
                thumbnailUrl = command.thumbnailUrl,
                isPublic = command.isPublic,
                slug = Slug.Companion.of(command.slug),
            ),
            title = command.title,
            diff = command.diff,
            fullContent = contentReconstructor.contentFromInitialDiff(command.diff),
            versionId = idGenerator.generateId()
        ).publish(slugUniquenessChecker::checkSlugUniqueness)
        .also { articleRepository.create(it) }

        eventRecorder.recordAll(article.pullEvents())

        return PublishArticleResult(
            article.id,
            article.currentVersion.id
        )
    }

    @Transactional
    override suspend fun update(command: UpdatePublishCommand): PublishArticleResult {
        val createdAt = Instant.now()

        val hasMeaningfulDiff = diffProcessor.hasValidDelta(command.diff)

        val existingArticle = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)


        val updatedArticle = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateMetadata(
                ArticleMetadata(
                    folderId = command.folderId,
                    summary = command.summary,
                    thumbnailUrl = command.thumbnailUrl,
                    isPublic = command.isPublic,
                    slug = Slug.Companion.of(command.slug)
                )
            )
            .updateVersionIfChanged(
                title = command.title,
                hasMeaningfulDiff = hasMeaningfulDiff,
                diff = command.diff,
                generateId = idGenerator::generateId,
                createdAt = createdAt,
                shouldRebase =  rebasePolicy::shouldStoreAsBase,
                reconstructFullContent = contentReconstructor::reconstructFullContent
            )
            .publish(slugUniquenessChecker::checkSlugUniqueness)
            .also {
                articleRepository.update(it)
            }

        eventRecorder.recordAll(updatedArticle.pullEvents())

        return PublishArticleResult(
            updatedArticle.id,
            updatedArticle.currentVersion.id
        )
    }
}