package dev.codeswamp.core.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.VersionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface VersionJpaRepository : JpaRepository<VersionEntity, Long>{
    fun findAllByArticleId(articleId: Long): List<VersionEntity>
    fun findAllByIdIsIn(diffs: List<Long>): List<VersionEntity>
    fun countByArticleId(articleId: Long) : Long
    fun deleteAllByArticleId(articleId: Long)
}