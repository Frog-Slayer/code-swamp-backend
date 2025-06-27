package dev.codeswamp.articleprojection.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articleprojection.infrastructure.persistence.r2dbc.entity.OutboxEventEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OutboxEventR2dbcRepository : CoroutineCrudRepository<OutboxEventEntity, String> {

    @Query( """
        SELECT * from outbox_event
        WHERE status=:status
        ORDER BY created_at
        LIMIT :limit
       """)
    suspend fun findAllByStatus(
        @Param("status") status: String,
        @Param("limit") limit: Int
    ): List<OutboxEventEntity>


    @Modifying
    @Query( """
        UPDATE outbox_event
        SET status= :status
        WHERE id = :id
    """)
    suspend fun updateStatus(
        @Param("id") id: Long,
        @Param("status") status: String
    ) :Int

    @Modifying
    @Query( """
        UPDATE outbox_event
        SET retry_count=:retry_count + 1
        WHERE id = :id
        RETURNING retry_count
    """)
    suspend fun incrementRetryCount(id: Long) : Int
}