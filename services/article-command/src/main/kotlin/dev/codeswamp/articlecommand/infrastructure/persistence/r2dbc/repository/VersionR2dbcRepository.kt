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
        ON CONFLICT(id)
        DO UPDATE SET
            article_id = EXCLUDED.article_id,
            previous_version_id = EXCLUDED.previous_version_id,
            title = EXCLUDED.title,
            diff = EXCLUDED.diff,
            created_at = EXCLUDED.created_at,
            state = EXCLUDED.state,
            is_base = EXCLUDED.is_base
        RETURNING *
    """)
    suspend fun upsert(
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
    suspend fun findByArticleIdAndStateIs(articleId: Long, state: VersionStateJpa): VersionEntity?
    suspend fun findAllByIdIsIn(diffs: List<Long>): List<VersionEntity>
    suspend fun countByArticleId(articleId: Long): Long
    suspend fun deleteAllByArticleId(articleId: Long)
    suspend fun deleteAllByArticleIdIn(articleIds: List<Long>)
}