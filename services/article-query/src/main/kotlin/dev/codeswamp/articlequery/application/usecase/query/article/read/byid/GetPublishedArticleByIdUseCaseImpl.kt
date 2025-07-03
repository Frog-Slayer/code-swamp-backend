package dev.codeswamp.articlequery.application.usecase.query.article.read.byid

import dev.codeswamp.articlequery.application.exception.article.ArticleNotFoundException
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articlequery.application.usecase.query.article.read.ReadArticleResult
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleByIdUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository,
) : GetPublishedArticleByIdUseCase {
    override suspend fun handle(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        val article = publishedArticleRepository.findByArticleId(query.articleId)
            ?: throw ArticleNotFoundException.Companion.byId(query.articleId)

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