package dev.codeswamp.core.article.application.usecase.command.publish

import dev.codeswamp.core.article.domain.article.exceptions.ArticleNotFoundException
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.core.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class PublishArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val contentReconstructor: ArticleContentReconstructor,
    private val eventPublisher: ApplicationEventPublisher,
) : PublishArticleUseCase {

    override fun update(command: UpdatePublishCommand) : PublishArticleResult {
        val createdAt = Instant.now()

        val article = articleRepository.findByIdAndVersionId(command.articleId, command.versionId )
            ?.apply { checkOwnership(command.userId) }
            ?.updateMetadata(ArticleMetadata(
                folderId = command.folderId,
                summary = command.summary,
                thumbnailUrl = command.thumbnailUrl,
                isPublic = command.isPublic,
                slug = Slug.of(command.slug)
            ))
            ?.updateVersionIfChanged(command.title, command.diff, idGenerator::generateId, createdAt )
            ?.publish(slugUniquenessChecker::checkSlugUniqueness)
            ?: throw ArticleNotFoundException("Draft 저장에 실패했습니다 ")

        val saved = articleRepository.save(article)
        article.pullEvents().forEach(eventPublisher::publishEvent)

        return PublishArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

    override fun create(command: CreatePublishCommand): PublishArticleResult {
        val createdAt = Instant.now()

        val article = VersionedArticle.create(
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
            fullContent = contentReconstructor.applyDiff("",  command.diff),
            versionId = idGenerator.generateId()
        ).publish(slugUniquenessChecker::checkSlugUniqueness)

        val saved = articleRepository.save(article)

        article.pullEvents().forEach(eventPublisher::publishEvent)

        return PublishArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }
}