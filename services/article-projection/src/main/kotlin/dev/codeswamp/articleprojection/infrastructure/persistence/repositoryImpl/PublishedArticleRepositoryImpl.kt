package dev.codeswamp.articleprojection.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articleprojection.application.readmodel.model.PublishedArticle
import dev.codeswamp.articleprojection.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articleprojection.infrastructure.persistence.r2dbc.repository.PublishedArticleR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class PublishedArticleRepositoryImpl(
    private val publishedArticleR2dbcRepository: PublishedArticleR2dbcRepository
) : PublishedArticleRepository {

    override suspend fun save(article : PublishedArticle): PublishedArticle {
        return publishedArticleR2dbcRepository.upsert(
            id = article.id,
            authorId = article.authorId,
            folderId = article.folderId,
            createdAt = article.createdAt,
            updatedAt = article.updatedAt,
            summary = article.summary,
            thumbnail = article.thumbnail,
            isPublic = article.isPublic,
            slug = article.slug,
            title = article.title,
            content = article.content
        ).toDomain()
    }
}