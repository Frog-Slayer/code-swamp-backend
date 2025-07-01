package dev.codeswamp.framework.infrastructure.persistence.r2dbc

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.framework.application.outbox.EventTypeRegistry
import dev.codeswamp.framework.application.outbox.OutboxEvent
import java.time.Instant

data class OutboxEventEntity (
    val id: Long,
    val key : String,
    val eventType: String,
    val payloadJson: String,
    val status: String,
    val createdAt: Instant,
    val retryCount: Int,
    val serviceName: String
)


class OutboxEventMapper(
    private val eventTypeRegistry: EventTypeRegistry,
    private val objectMapper: ObjectMapper
) {

    fun toOutboxEvent(entity: OutboxEventEntity ) : OutboxEvent {
        val (id, key, eventType, payloadJson,
            status, createdAt, retryCount, serviceName) = entity

        val clazz = eventTypeRegistry.getClassFor(eventType)

        return OutboxEvent(
            id = id,
            eventType = eventType,
            payload = objectMapper.readValue(payloadJson, clazz) as BusinessEvent,
            status = OutboxEvent.EventStatus.valueOf(status),
            createdAt = createdAt,
            retryCount = retryCount,
            serviceName = serviceName,
            key = key
        )
    }

    fun toEntity(event: OutboxEvent) : OutboxEventEntity {
        return  OutboxEventEntity(
            id = event.id,
            key = event.key,
            eventType = event.eventType,
            payloadJson = objectMapper.writeValueAsString(event.payload),
            status = event.status.name,
            createdAt = event.createdAt,
            retryCount = event.retryCount,
            serviceName = event.serviceName
        )
    }
}