package dev.codeswamp.articlecommand.application.event.outbox

import java.util.UUID

interface OutboxEventRepository {
    suspend fun save(event: OutboxEvent)
    suspend fun findPending(limit: Int): List<OutboxEvent>
    suspend fun markAsProcessed(id: Long)
    suspend fun markAsFailed(id: Long)
}