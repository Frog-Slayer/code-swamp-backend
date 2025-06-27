package dev.codeswamp.articleprojection.application.event.outbox.outbox

import dev.codeswamp.core.application.SystemIdGenerator
import dev.codeswamp.core.application.event.outbox.DefaultEventRecorder
import dev.codeswamp.core.application.event.outbox.EventTypeRegistry
import dev.codeswamp.core.application.event.outbox.OutboxEvent
import dev.codeswamp.core.application.event.outbox.OutboxEventRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class EventRecorderImpl(
    outboxRepository: OutboxEventRepository,
    systemIdGenerator: SystemIdGenerator,
    private val keyResolver: EventKeyResolver,
    @Value("\${spring.application.name}") private val appName: String,
): DefaultEventRecorder (outboxRepository, appName, systemIdGenerator) {

    fun generateEventKey(event: dev.codeswamp.core.common.event.BusinessEvent) : String {
        return keyResolver.resolveKey(event)
    }

     override fun createOutboxEvent(event: dev.codeswamp.core.common.event.BusinessEvent) : OutboxEvent {
        val eventType = EventTypeRegistry.register(event)

        return OutboxEvent.create(
            { systemIdGenerator.generateId() },
            eventType = eventType,
            payload = event,
            createdAt = Instant.now(),
            serviceName = serviceName,
            key = generateEventKey(event)
        )
    }
}
