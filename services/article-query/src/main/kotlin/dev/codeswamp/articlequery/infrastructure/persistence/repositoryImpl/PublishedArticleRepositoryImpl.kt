package dev.codeswamp.articlequery.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle
import dev.codeswamp.articlequery.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository.PublishedArticleR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class PublishedArticleRepositoryImpl(
    private val publishedArticleR2dbcRepository: PublishedArticleR2dbcRepository
) : PublishedArticleRepository {
    override suspend fun findByArticleId(articleId: Long): PublishedArticle? {
        return publishedArticleR2dbcRepository.findById(articleId)?.toDomain()
    }

    override suspend fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle? {
        return publishedArticleR2dbcRepository.findByFolderIdAndSlug(folderId, slug)?.toDomain()
    }
}