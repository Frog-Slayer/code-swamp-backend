package dev.codeswamp.core.application.event.outbox

import dev.codeswamp.core.application.SystemIdGenerator
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.application.event.EventRecorder
import java.time.Instant
import java.util.UUID

abstract class DefaultEventRecorder(
    val outboxRepository: OutboxEventRepository,
    val serviceName: String,
    val systemIdGenerator: SystemIdGenerator
) : EventRecorder {

    override suspend fun record(event: BusinessEvent) {
        val outboxEvent = createOutboxEvent(event)
        outboxRepository.save(outboxEvent)
    }

    override suspend fun recordAll(events: List<BusinessEvent>) {
        val outboxEvents = events.map {  event ->
            createOutboxEvent(event)
        }

        outboxRepository.saveAll(outboxEvents)
    }

    fun generateUUIDKey() : String {
        return "$serviceName-${UUID.randomUUID()}"
    }

    open fun createOutboxEvent(event: BusinessEvent) : OutboxEvent {
        val eventType = EventTypeRegistry.register(event)

        return OutboxEvent.create(
            { systemIdGenerator.generateId() },
            eventType = eventType,
            payload = event,
            createdAt = Instant.now(),
            serviceName = serviceName,
            key = generateUUIDKey()
        )
    }
}