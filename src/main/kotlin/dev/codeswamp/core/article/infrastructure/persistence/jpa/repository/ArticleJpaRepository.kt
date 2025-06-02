package dev.codeswamp.core.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleJpaRepository : JpaRepository<ArticleEntity, Long>{
    fun findAllByIdIsIn(articleIds: List<Long>): List<ArticleEntity>

    fun findByFolderIdAndSlug(folderId: Long, slug: String): ArticleEntity?
}