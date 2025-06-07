package dev.codeswamp.core.article.application.usecase.query.read.byid

import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.application.usecase.query.read.ReadArticleResult
import dev.codeswamp.core.article.domain.article.exceptions.ArticleNotFoundException
import org.springframework.stereotype.Service

@Service
class GetPublishedArticleByIdUseCaseImpl(
    private val publishedArticleRepository: PublishedArticleRepository
) : GetPublishedArticleByIdUseCase {
    override fun handle(query: GetPublishedArticleByIdQuery): ReadArticleResult {
        val article = publishedArticleRepository.findByArticleId(query.articleId)
            ?: throw ArticleNotFoundException("해당 경로의 글을 찾을 수 없습니다.")

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