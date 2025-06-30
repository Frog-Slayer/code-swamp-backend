package dev.codeswamp.framework.application.port.outgoing

import dev.codeswamp.framework.application.outbox.OutboxEvent

interface OutboxEventRepository {
    suspend fun insert(event: OutboxEvent)
    suspend fun insertAll(events: List<OutboxEvent>)
    suspend fun findPending(limit: Int): List<OutboxEvent>
    suspend fun markAsSent(id: Long)
    suspend fun markAsFailed(id: Long)
    suspend fun incrementRetryCount(id: Long) : Int
}