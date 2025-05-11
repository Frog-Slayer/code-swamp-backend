package dev.codeswamp.core.article.infrastructure.persistence.jpa

import dev.codeswamp.core.article.infrastructure.persistence.entity.ArticleDiffEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ArticleDiffJpaRepository : JpaRepository<ArticleDiffEntity, Long>{
    fun findAllByArticleId(articleId: Long): List<ArticleDiffEntity>
    fun findAllByIdIsIn(diffs: List<Long>): List<ArticleDiffEntity>

    fun countByArticleId(articleId: Long) : Long

    @Transactional
    fun deleteAllByArticleId(articleId: Long)
}