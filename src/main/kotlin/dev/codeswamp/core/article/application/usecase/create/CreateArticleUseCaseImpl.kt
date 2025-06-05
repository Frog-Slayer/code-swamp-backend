package dev.codeswamp.core.article.application.usecase.create

import dev.codeswamp.core.article.application.dto.command.CreateArticleCommand
import dev.codeswamp.core.article.domain.article.model.VersionedArticle
import dev.codeswamp.core.article.domain.article.model.vo.ArticleMetadata
import dev.codeswamp.core.article.domain.article.model.vo.Slug
import dev.codeswamp.core.article.domain.article.model.vo.Title
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.article.service.SlugUniquenessChecker
import dev.codeswamp.core.article.domain.support.IdGenerator
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CreateArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
    private val idGenerator: IdGenerator,
    private val slugUniquenessChecker: SlugUniquenessChecker

) : CreateArticleUseCase {

    override fun handle(command: CreateArticleCommand) {
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
                title = Title.Companion.of(command.title),
                slug = Slug.Companion.of(command.slug),
            ),
        )
        .updateVersionIfChanged(command.content, { idGenerator.generateId()  }, createdAt)
        .let {
            if (command.type == "DRAFT") it.draft(slugUniquenessChecker::checkSlugUniqueness)
            else it.publish(slugUniquenessChecker::checkSlugUniqueness)
        }

        articleRepository.save(article)
    }


}