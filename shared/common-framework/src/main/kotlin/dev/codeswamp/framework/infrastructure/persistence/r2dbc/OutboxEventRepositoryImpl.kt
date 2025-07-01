package dev.codeswamp.framework.infrastructure.persistence.r2dbc

import com.fasterxml.jackson.databind.JsonNode
import dev.codeswamp.framework.application.outbox.OutboxEvent
import dev.codeswamp.framework.application.port.outgoing.OutboxEventRepository
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.Row
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.DatabaseClient
import java.time.Instant

class OutboxEventRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val mapper: OutboxEventMapper,
) : OutboxEventRepository {

    override suspend fun insert(event: OutboxEvent) {
        val entity = mapper.toEntity(event)

        databaseClient.sql("""
            INSERT INTO outbox_event (id, event_key, event_type, payload_json, status, created_at, retry_count, service_name)
            VALUES (:id, :event_key, :event_type, :payload, :status, :created_at, :retry_count, :service_name)
        """)
            .bind("id", entity.id)
            .bind("event_key", entity.key)
            .bind("event_type", entity.eventType)
            .bind("payload", Json.of(entity.payloadJson))
            .bind("status", entity.status)
            .bind("created_at", entity.createdAt)
            .bind("retry_count", entity.retryCount)
            .bind("service_name", entity.serviceName)
            .fetch()
            .rowsUpdated()
            .awaitFirstOrNull()
    }

    override suspend fun insertAll(events: List<OutboxEvent>) {
        if (events.isEmpty()) return

        val entities = events.map { mapper.toEntity(it) }

        val ids = entities.map { it.id }
        val types = entities.map { it.eventType }
        val payloads = entities.map { it.payloadJson }
        val statuses = entities.map { it.status }
        val createdAts = entities.map { it.createdAt }
        val retryCounts = entities.map { it.retryCount }
        val keys = entities.map { it.key }
        val serviceNames = entities.map { it.serviceName }

        databaseClient.sql("""
            INSERT INTO outbox_event
            (id, event_type, payload_json, status, created_at, retry_count, event_key, service_name)
            SELECT * FROM UNNEST(
                $1::bigint[],
                $2::text[],
                $3::text[]::jsonb[],
                $4::text[],
                $5::timestamptz[],
                $6::int[],
                $7::text[],
                $8::text[]
            )
        """)
            .bind("$1", ids.toTypedArray())
            .bind("$2", types.toTypedArray())
            .bind("$3", payloads.toTypedArray())
            .bind("$4", statuses.toTypedArray())
            .bind("$5", createdAts.toTypedArray())
            .bind("$6", retryCounts.toTypedArray())
            .bind("$7", keys.toTypedArray())
            .bind("$8", serviceNames.toTypedArray())
            .fetch()
            .rowsUpdated()
            .awaitFirstOrNull()
    }

    override suspend fun findPending(limit: Int): List<OutboxEvent> {
        return findByStatusWithLimit(limit, OutboxEvent.EventStatus.PENDING).map {
            mapper.toOutboxEvent(it)
        }
    }

    override suspend fun markAsSent(id: Long) {
        updateStatus(id,  OutboxEvent.EventStatus.SENT)
    }

    override suspend fun markAsFailed(id: Long) {
        updateStatus(id,  OutboxEvent.EventStatus.FAILED)
    }

    private suspend fun findByStatusWithLimit(limit: Int, status: OutboxEvent.EventStatus): List<OutboxEventEntity>  {
        return databaseClient.sql("""
            SELECT * from outbox_event
            WHERE status = :status
            ORDER BY created_at
            LIMIT :limit
        """)
            .bind("status", status.name )
            .bind("limit", limit)
            .map { row, _ -> mapRowToOutboxEvent(row)}
            .all()
            .collectList()
            .awaitFirst()
    }


    private suspend fun updateStatus(id: Long, status: OutboxEvent.EventStatus) {
         databaseClient.sql("""
            UPDATE outbox_event
            SET status = :status
            WHERE id = :id
        """)
            .bind("id", id)
            .bind("status", status.name )
            .fetch()
            .rowsUpdated()
            .awaitFirstOrNull()
    }

    override suspend fun incrementRetryCount(id: Long) : Int {
        return databaseClient.sql("""
             UPDATE outbox_event
             SET retry_count = retry_count + 1
             WHERE id = :id
             RETURNING retry_count           
        """).bind("id", id)
            .map { row, _ -> row.get("retry_count", Int::class.javaObjectType)!! }
            .one()
            .awaitFirst()
    }

    private fun mapRowToOutboxEvent(row : Row) : OutboxEventEntity {
        return OutboxEventEntity(
            id = row.get("id", Long::class.java)!!,
            eventType = row.get("event_type", String::class.java)!!,
            serviceName = row.get("service_name", String::class.java)!!,
            key = row.get("event_key", String::class.java)!!,
            payloadJson = row.get("payload_json", String::class.java)!!,
            status = row.get("status", String::class.java)!!,
            createdAt = row.get("created_at", Instant::class.java)!!,
            retryCount = row.get("retry_count", Int::class.javaObjectType)!!,
        )
    }
}