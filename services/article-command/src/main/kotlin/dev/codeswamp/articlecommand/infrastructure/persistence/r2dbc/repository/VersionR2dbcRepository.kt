package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.VersionStateJpa
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface VersionR2dbcRepository : CoroutineCrudRepository<VersionEntity, Long> {

    @Query("""
        INSERT INTO version (id, article_id, previous_version_id, title, diff, created_at, state, is_base)
        VALUES (:id, :articleId, :prevVersionId, :title, :diff, :createdAt, :state, :isBase)
        RETURNING *
    """)
    suspend fun insert(
        @Param("id") id: Long,
        @Param("articleId") articleId: Long,
        @Param("prevVersionId") prevVersionId: Long?,
        @Param("title") title: String?,
        @Param("diff") diff: String,
        @Param("createdAt") createdAt: Instant,
        @Param("state") state: String,
        @Param("isBase") isBaseVersion: Boolean
    ): VersionEntity

    suspend fun findAllByArticleId(articleId: Long): List<VersionEntity>
    suspend fun findTopByArticleIdAndIdLessThanAndStateOrderByIdDesc(articleId: Long, id: Long, state: VersionStateJpa): VersionEntity?
    suspend fun findAllByIdIsIn(diffs: List<Long>): List<VersionEntity>
    suspend fun countByArticleId(articleId: Long): Long
    suspend fun deleteAllByArticleId(articleId: Long)
    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
}