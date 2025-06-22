package dev.codeswamp.articlecommand.application.event.outbox

interface OutboxEventRepository {
    suspend fun save(event: OutboxEvent)
    suspend fun findPending(limit: Int): List<OutboxEvent>
    suspend fun markAsSent(id: Long)
    suspend fun markAsFailed(id: Long)
    suspend fun incrementRetryCount(id: Long) : Int
}