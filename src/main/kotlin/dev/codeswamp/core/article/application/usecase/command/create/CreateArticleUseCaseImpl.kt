package dev.codeswamp.core.article.application.usecase.command.create

import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.model.vo.Title
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.ArticleContentReconstructor
import dev.codeswamp.core.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CreateArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val contentReconstructor: ArticleContentReconstructor,
    private val slugUniquenessChecker: SlugUniquenessChecker

) : CreateArticleUseCase {

    override fun handle(command: CreateArticleCommand): CreateArticleResult {
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
        )
        .let {
            if (command.type == "DRAFT") it.draft(slugUniquenessChecker::checkSlugUniqueness)
            else it.publish(slugUniquenessChecker::checkSlugUniqueness)
        }

        val saved = articleRepository.save(article)

        return CreateArticleResult(
            saved.id,
            saved.currentVersion?.id ?: throw IllegalStateException("currentVersion is null"),
        )
    }
}