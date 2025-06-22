package dev.codeswamp.articlecommand.application.event.outbox

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.core.domain.DomainEvent
import java.time.Instant

data class OutboxEvent (
    val id: Long = SnowflakeIdGenerator.generateId(),
    val eventType: String,
    val payload: Event,
    val status: EventStatus = EventStatus.PENDING,
    val createdAt : Instant,
) {
    enum class EventStatus {
        PENDING,
        PROCESSING,
        PROCESSED,
        FAILED
    }

    companion object {
        fun registerAndCreate(event: DomainEvent) : OutboxEvent {
            val className = EventTypeRegistry.register(event)

            return OutboxEvent(
                eventType = className,
                payload = event,
                createdAt = Instant.now()
            )
        }
    }
}