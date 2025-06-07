package dev.codeswamp.core.article.application.usecase.command.draft

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
class DraftArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker,
    private val contentReconstructor: ArticleContentReconstructor,
    private val eventPublisher: ApplicationEventPublisher,
) : DraftArticleUseCase {

    override fun handle(command: DraftArticleCommand): DraftArticleResult {
        return if (command.articleId != null && command.versionId != null) update(command)
        else create(command)
    }

    private fun update(command: DraftArticleCommand) : DraftArticleResult {
        require(command.articleId != null && command.versionId != null) { "articleId and versionId must not be null" }

        val createdAt = Instant.now()

        val article = articleRepository.findByIdAndVersionId(command.articleId, command.versionId )
            ?.apply { checkOwnership(command.userId) }
            ?.updateVersionIfChanged(command.title, command.diff, idGenerator::generateId, createdAt )
            ?.draft(slugUniquenessChecker::checkSlugUniqueness)
            ?: throw ArticleNotFoundException("Draft 저장에 실패했습니다 ")

        val saved = articleRepository.save(article)
        article.pullEvents().forEach(eventPublisher::publishEvent)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }

    private fun create(command: DraftArticleCommand): DraftArticleResult {
        val createdAt = Instant.now()

        val article = VersionedArticle.create(
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
            fullContent = contentReconstructor.applyDiff("",  command.diff),
            versionId = idGenerator.generateId()
        ).draft(slugUniquenessChecker::checkSlugUniqueness)

        val saved = articleRepository.save(article)

        article.pullEvents().forEach(eventPublisher::publishEvent)

        return DraftArticleResult(
            saved.id,
            saved.currentVersion.id
        )
    }
}