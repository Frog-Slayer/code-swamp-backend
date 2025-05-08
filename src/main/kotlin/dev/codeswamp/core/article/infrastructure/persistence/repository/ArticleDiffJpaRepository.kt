package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleDiffJpaRepository : JpaRepository<ArticleDiffEntity, Long>{
    fun findByArticleIdAndVersion(articleId: Long, version: Long): ArticleDiffEntity?
    fun findAllByArticleId(articleId: Long): List<ArticleDiffEntity>


    @Query ("""
        SELECT COALESCE(MAX(a.version), 0)
        FROM ArticleDiffEntity a
        WHERE a.article.id = :articleId
    """)
    fun findLatestVersionByArticleId(articleId: Long): Int
}