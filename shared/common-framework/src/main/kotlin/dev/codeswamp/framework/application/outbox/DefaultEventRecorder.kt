package dev.codeswamp.framework.application.outbox

import dev.codeswamp.core.application.SystemIdGenerator
import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.framework.application.port.outgoing.OutboxEventRepository
import java.time.Instant

class DefaultEventRecorder (
    val outboxRepository: OutboxEventRepository,
    val serviceName: String,
    val eventKeyResolver: EventKeyResolver,
    val systemIdGenerator: SystemIdGenerator,
    val eventTypeRegistry: EventTypeRegistry,
) : EventRecorder {

    override suspend fun record(event: BusinessEvent) {
        val outboxEvent = createOutboxEvent(event)
        outboxRepository.insert(outboxEvent)
    }

    override suspend fun recordAll(events: List<BusinessEvent>) {
        val outboxEvents = events.map {  event ->
            createOutboxEvent(event)
        }

        outboxRepository.insertAll(outboxEvents)
    }

    fun generateEventKey(event: BusinessEvent) : String {
        return eventKeyResolver.resolveKey(event)
    }

     fun createOutboxEvent(event: BusinessEvent) : OutboxEvent {
        val eventType = eventTypeRegistry.getEventTypeFor(event::class.java)

        return OutboxEvent.create(
            systemIdGenerator::generateId,
            eventType = eventType,
            payload = event,
            createdAt = Instant.now(),
            serviceName = serviceName,
            key = generateEventKey(event)
        )
    }
}