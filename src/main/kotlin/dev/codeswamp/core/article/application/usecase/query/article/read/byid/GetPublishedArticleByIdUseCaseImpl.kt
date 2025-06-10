package dev.codeswamp.core.article.application.usecase.query.article.read.byid

import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.usecase.query.article.read.ReadArticleResult
import dev.codeswamp.core.article.application.exception.article.ArticleNotFoundException
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleByIdUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository
) : GetPublishedArticleByIdUseCase {
    override fun handle(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        val article = publishedArticleRepository.findByArticleId(query.articleId)
            ?: throw ArticleNotFoundException.byId(query.articleId)

        article.assertReadableBy(query.userId)

        return ReadArticleResult(
            id = article.id,
            authorId = article.authorId,
            createdAt = article.createdAt,
            updatedAt = article.createdAt,
            folderId = article.folderId,
            summary = article.summary,
            thumbnailUrl = article.thumbnailUrl,
            isPublic = article.isPublic,
            title = article.title,
            content = article.content,
        )
    }
}