package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.OutboxEventEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.OutboxEventR2dbcRepository
import dev.codeswamp.core.application.event.outbox.OutboxEvent
import dev.codeswamp.core.application.event.outbox.OutboxEventRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class OutboxEventRepositoryImpl(
    private val outboxEventRepository: OutboxEventR2dbcRepository,
    private val databaseClient: DatabaseClient
): OutboxEventRepository {

    override suspend fun save(event: OutboxEvent) {
        val entity = OutboxEventEntity.from(event)
        outboxEventRepository.save(entity)
    }

    override suspend fun saveAll(events: List<OutboxEvent>) {
        if (events.isEmpty()) return
        val entities = events.map { OutboxEventEntity.from(it) }

        databaseClient.inConnectionMany { connection ->
            val statement = connection.createStatement("""
                    INSERT INTO article_outbox(id, event_type, payload_json, status, created_at, retry_count) 
                    VALUES ($1, $2, $3, $4, $5, $6)
                    """)

            entities.forEach { entity ->
                statement.bind(0, entity.id)
                    .bind(1, entity.eventType)
                    .bind(2, entity.payloadJson)
                    .bind(3, entity.status)
                    .bind(4, entity.createdAt)
                    .bind(5, entity.retryCount)
                    .add()
            }

            Flux.from(statement.execute())
        }.awaitFirstOrNull()
    }

   override suspend fun findPending(limit: Int): List<OutboxEvent> {
        return outboxEventRepository.findAllByStatus(
            OutboxEvent.EventStatus.PENDING.toString(), limit )
            .map { it.toOutboxEvent() }
    }

    override suspend fun markAsSent(id: Long) {
        outboxEventRepository.updateStatus(id,  OutboxEvent.EventStatus.SENT.toString())
    }

    override suspend fun markAsFailed(id: Long) {
        outboxEventRepository.updateStatus(id,  OutboxEvent.EventStatus.FAILED.toString())
    }

    override suspend fun incrementRetryCount(id: Long): Int {
        return outboxEventRepository.incrementRetryCount(id)
    }
}