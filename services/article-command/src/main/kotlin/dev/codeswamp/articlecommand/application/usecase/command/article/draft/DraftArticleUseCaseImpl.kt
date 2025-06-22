package dev.codeswamp.articlecommand.application.usecase.command.article.draft

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.application.port.outgoing.InternalEventPublisher
import dev.codeswamp.articlecommand.application.rebase.RebasePolicy
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.articlecommand.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.articlecommand.domain.support.DiffProcessor
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
    private val rebasePolicy: RebasePolicy,
    private val eventPublisher: InternalEventPublisher,
) : DraftArticleUseCase {

    @Transactional
    override suspend fun create(command: CreateDraftCommand): DraftArticleResult {
        val createdAt = Instant.now()

        val article = VersionedArticle.Companion.create(
            authorId = command.userId,
            id = idGenerator.generateId(),
            createdAt = createdAt,
            metadata = ArticleMetadata(
                folderId = command.folderId,
                summary = "",
                thumbnailUrl = null,
                isPublic = false,
                slug = null,
            ),
            title = command.title,
            diff = command.diff,
            fullContent = contentReconstructor.contentFromInitialDiff(command.diff),
            versionId = idGenerator.generateId()
        ).draft(slugUniquenessChecker::checkSlugUniqueness)
        .also { articleRepository.create(it) }

        article.pullEvents().forEach(eventPublisher::publish)

        return DraftArticleResult(
            article.id,
            article.currentVersion.id
        )
    }

    @Transactional
    override suspend fun update(command: UpdateDraftCommand): DraftArticleResult {
        val createdAt = Instant.now()

        val hasMeaningfulDiff = diffProcessor.hasValidDelta(command.diff)

        val existingArticle = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val updatedArticle = existingArticle
            .apply { checkOwnership(command.userId) }
            .updateVersionIfChanged(
                title = command.title,
                hasMeaningfulDiff = hasMeaningfulDiff,
                diff = command.diff,
                generateId = idGenerator::generateId,
                createdAt = createdAt,
                shouldRebase =  rebasePolicy::shouldStoreAsBase,
                reconstructFullContent = contentReconstructor::reconstructFullContent
            )
            .draft(slugUniquenessChecker::checkSlugUniqueness)
            .also {
                articleRepository.save(it)
            }

        updatedArticle.pullEvents().forEach(eventPublisher::publish)

        return DraftArticleResult(
            updatedArticle.id,
            updatedArticle.currentVersion.id
        )
    }
}