package dev.codeswamp.core.article.infrastructure.persistence.repository

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleDiffJpaRepository : JpaRepository<ArticleDiffEntity, Long>{
    fun findByArticleIdAndVersion(articleId: Long, version: Long): ArticleDiffEntity?
    fun findAllByArticleId(articleId: Long): List<ArticleDiffEntity>
}