package dev.codeswamp.articlecommand.application.usecase.command.article.draft

import dev.codeswamp.articlecommand.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlecommand.application.port.outgoing.EventPublisher
import dev.codeswamp.articlecommand.application.rebase.RebasePolicy
import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import dev.codeswamp.articlecommand.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.articlecommand.domain.article.repository.ArticleRepository
import dev.codeswamp.articlecommand.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.articlecommand.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.articlecommand.domain.support.IdGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class DraftArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val contentReconstructor: ArticleContentReconstructor,
    private val rebasePolicy: RebasePolicy,
    private val eventPublisher: EventPublisher,
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

        val saved = articleRepository.save(article)
        article.pullEvents().forEach(eventPublisher::publish)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

    @Transactional
    override suspend fun update(command: UpdateDraftCommand): DraftArticleResult {
        val createdAt = Instant.now()

        val article = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?.apply { checkOwnership(command.userId) }
            ?.updateVersionIfChanged(
                command.title,
                command.diff,
                idGenerator::generateId,
                createdAt,
                rebasePolicy::shouldStoreAsBase,
                contentReconstructor::reconstructFullContent
            )
            ?.draft(slugUniquenessChecker::checkSlugUniqueness)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val saved = articleRepository.save(article)
        article.pullEvents().forEach(eventPublisher::publish)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

}