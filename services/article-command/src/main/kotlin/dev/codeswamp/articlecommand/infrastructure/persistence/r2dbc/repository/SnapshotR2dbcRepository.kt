package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.BaseVersionEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SnapshotR2dbcRepository : CoroutineCrudRepository<BaseVersionEntity, Long> {

    @Query( """
        INSERT INTO snapshot (version_id, content) VALUES (:version_id, :content) RETURNING *
    """)
    fun insert(
        @Param("versionId") versionId: Long,
        @Param("content") content: String,
    ) : BaseVersionEntity


}
