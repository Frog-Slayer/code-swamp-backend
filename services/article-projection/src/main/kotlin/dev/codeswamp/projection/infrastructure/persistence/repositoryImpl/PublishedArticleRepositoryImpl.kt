package dev.codeswamp.projection.infrastructure.persistence.repositoryImpl

import dev.codeswamp.projection.application.readmodel.model.PublishedArticle
import dev.codeswamp.projection.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.projection.infrastructure.persistence.r2dbc.repository.PublishedArticleR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class PublishedArticleRepositoryImpl(
    private val publishedArticleR2dbcRepository: PublishedArticleR2dbcRepository
) : PublishedArticleRepository {

    override suspend fun findByArticleId(articleId: Long): PublishedArticle? {
        return publishedArticleR2dbcRepository.findById(articleId)?.toDomain()
    }

    override suspend fun save(article : PublishedArticle): PublishedArticle {
        return publishedArticleR2dbcRepository.upsert(
            id = article.id,
            versionId =  article.versionId,
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

    override suspend fun deleteByArticleId(articleId: Long) {
       publishedArticleR2dbcRepository.deleteById(articleId)
    }

    override suspend fun deleteAllByFolderIds(folderIds: List<Long>) {
        publishedArticleR2dbcRepository.deleteAllByFolderIdIsIn(folderIds)
    }
}