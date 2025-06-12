package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.application.readmodel.model.PublishedArticle
import dev.codeswamp.core.article.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.PublishedArticleEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.PublishedArticleJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PublishedArticleRepositoryImpl(
    private val publishedArticleJpaRepository: PublishedArticleJpaRepository
): PublishedArticleRepository {
    override fun save(publishedArticle: PublishedArticle): PublishedArticle {
        return publishedArticleJpaRepository.save(PublishedArticleEntity.from(publishedArticle)).toDomain()
    }

    override fun findByArticleId(articleId: Long): PublishedArticle? {
        return publishedArticleJpaRepository.findByIdOrNull(articleId)?.toDomain()
    }

    override fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticle? {
        return publishedArticleJpaRepository.findByFolderIdAndSlug(folderId, slug)?.toDomain()
    }
}