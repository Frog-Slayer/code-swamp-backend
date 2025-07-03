package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VersionR2dbcRepository : CoroutineCrudRepository<VersionEntity, Long> {
    suspend fun findAllByArticleId(articleId: Long): List<VersionEntity>
    suspend fun deleteAllByArticleId(articleId: Long)
    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
}