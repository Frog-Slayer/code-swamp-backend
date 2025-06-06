package dev.codeswamp.core.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleMetadataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleMetadataJpaRepository : JpaRepository<ArticleMetadataEntity, Long>{
    fun findAllByIdIsIn(articleIds: List<Long>): List<ArticleMetadataEntity>
    fun findIdByFolderIdAndSlug(folderId: Long, slug: String): Long?
}