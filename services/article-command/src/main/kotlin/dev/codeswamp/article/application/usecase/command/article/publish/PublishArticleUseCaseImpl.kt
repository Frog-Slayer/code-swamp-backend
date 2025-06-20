package dev.codeswamp.article.application.usecase.command.article.publish

import dev.codeswamp.article.application.exception.article.ArticleNotFoundException
import dev.codeswamp.article.application.rebase.RebasePolicy
import dev.codeswamp.article.domain.article.model.VersionedArticle
import dev.codeswamp.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.article.domain.article.model.vo.Slug
import dev.codeswamp.article.domain.article.repository.ArticleRepository
import dev.codeswamp.article.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.article.domain.support.IdGenerator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PublishArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val contentReconstructor: ArticleContentReconstructor,
    private val rebasePolicy: RebasePolicy,
    private val eventPublisher: ApplicationEventPublisher,
) : PublishArticleUseCase {

    @Transactional
    override fun create(command: CreatePublishCommand): PublishArticleResult {
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

        val saved = articleRepository.save(article)

        article.pullEvents().forEach(ApplicationEventPublisher::publishEvent)

        return PublishArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

    @Transactional
    override fun update(command: UpdatePublishCommand) : PublishArticleResult {
        val createdAt = Instant.now()

        val article = articleRepository.findByIdAndVersionId(command.articleId, command.versionId)
            ?.apply { VersionedArticle.checkOwnership(command.userId) }
            ?.updateMetadata(
                ArticleMetadata(
                    folderId = command.folderId,
                    summary = command.summary,
                    thumbnailUrl = command.thumbnailUrl,
                    isPublic = command.isPublic,
                    slug = Slug.Companion.of(command.slug)
                )
            )
            ?.updateVersionIfChanged(command.title,
                command.diff,
                idGenerator::generateId,
                createdAt,
                rebasePolicy::shouldStoreAsBase,
                contentReconstructor::reconstructFullContent)
            ?.publish(slugUniquenessChecker::checkSlugUniqueness)
            ?: throw ArticleNotFoundException.Companion.byId(command.articleId)

        val saved = articleRepository.save(article)
        article.pullEvents().forEach(ApplicationEventPublisher::publishEvent)

        return PublishArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }
}