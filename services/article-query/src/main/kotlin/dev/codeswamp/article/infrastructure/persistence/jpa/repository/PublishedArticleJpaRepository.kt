package dev.codeswamp.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.article.infrastructure.persistence.jpa.entity.PublishedArticleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PublishedArticleJpaRepository : JpaRepository<PublishedArticleEntity, Long> {
    fun findByFolderIdAndSlug(folderId: Long, slug: String): PublishedArticleEntity?
}