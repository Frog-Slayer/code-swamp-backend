package dev.codeswamp.article.application.usecase.command.article.draft

import dev.codeswamp.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.article.application.rebase.RebasePolicy
import dev.codeswamp.article.domain.article.model.VersionedArticle
import dev.codeswamp.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.article.domain.article.repository.ArticleRepository
import dev.codeswamp.article.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.article.domain.support.IdGenerator
import org.springframework.context.ApplicationEventPublisher
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
    private val eventPublisher: ApplicationEventPublisher,
) : DraftArticleUseCase {

    @Transactional
    override fun create(command: CreateDraftCommand): DraftArticleResult {
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

        article.pullEvents().forEach(ApplicationEventPublisher::publishEvent)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

    @Transactional
    override fun update(command: UpdateDraftCommand): DraftArticleResult {
        val createdAt = Instant.now()

        val article = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?.apply { VersionedArticle.checkOwnership(command.userId) }
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
        article.pullEvents().forEach(ApplicationEventPublisher::publishEvent)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

}