package dev.codeswamp.framework.application.event.outbox

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.event.BusinessEvent
import java.time.Instant

data class OutboxEvent (
    val id: Long,
    val serviceName: String,
    val key: String,
    val eventType: String,
    val payload: BusinessEvent,
    val status: EventStatus = EventStatus.PENDING,
    val createdAt : Instant,
    val retryCount: Int = 0,
) : ApplicationEvent {
    enum class EventStatus {
        PENDING,
        SENT,
        FAILED
    }

    companion object {
        fun create(
            generateId: () -> Long,
            eventType: String,
            payload: BusinessEvent,
            createdAt: Instant,
            serviceName: String,
            key: String,
        ) = OutboxEvent(
            id = generateId(),
            eventType = eventType,
            payload = payload,
            createdAt = createdAt,
            serviceName = serviceName,
            key = key,
        )
    }
}