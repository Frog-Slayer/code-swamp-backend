package dev.codeswamp.framework.application.event.outbox

import dev.codeswamp.core.application.SystemIdGenerator
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.application.event.eventbus.EventRecorder
import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import java.time.Instant

abstract class DefaultEventRecorder(
    val outboxRepository: OutboxEventRepository,
    val serviceName: String,
    val eventKeyResolver: EventKeyResolver,
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

    fun generateEventKey(event: BusinessEvent) : String {
        return eventKeyResolver.resolveKey(event)
    }

     fun createOutboxEvent(event: BusinessEvent) : OutboxEvent {
        val eventType = EventTypeRegistry.register(event)

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