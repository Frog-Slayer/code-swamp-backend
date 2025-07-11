package dev.codeswamp.framework.application.outbox

sealed class ProcessResult {
    data class Success(val eventId: Long) : ProcessResult()
    data class Retry(val eventId: Long, val currentRetryCount: Int) : ProcessResult()
    data class Failed(val eventId: Long, val reason: Throwable) : ProcessResult()
}

interface OutboxProcessor {
    suspend fun processOutbox() : List<ProcessResult>
}