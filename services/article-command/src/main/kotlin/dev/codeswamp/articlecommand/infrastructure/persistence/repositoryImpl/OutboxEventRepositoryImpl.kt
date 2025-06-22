package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.application.event.outbox.OutboxEvent
import dev.codeswamp.articlecommand.application.event.outbox.OutboxEventRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.OutboxEventEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.OutboxEventR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class OutboxEventRepositoryImpl(
    private val outboxEventRepository: OutboxEventR2dbcRepository
): OutboxEventRepository {
    override suspend fun save(event: OutboxEvent) {
        val entity = OutboxEventEntity.from(event)
        outboxEventRepository.save(entity)
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