package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionStateJpa
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VersionJpaRepository : CoroutineCrudRepository<VersionEntity, Long> {
    suspend fun findAllByArticleId(articleId: Long): List<VersionEntity>
    suspend fun findTopByArticleIdAndIdLessThanAndStateOrderByIdDesc(articleId: Long, id: Long, state: VersionStateJpa): VersionEntity?
    suspend fun findAllByIdIsIn(diffs: List<Long>): List<VersionEntity>
    suspend fun countByArticleId(articleId: Long): Long
    suspend fun deleteAllByArticleId(articleId: Long)
    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
}