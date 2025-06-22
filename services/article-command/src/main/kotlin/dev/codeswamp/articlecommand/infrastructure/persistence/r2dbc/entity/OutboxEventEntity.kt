package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.codeswamp.articlecommand.application.event.outbox.EventTypeRegistry
import dev.codeswamp.articlecommand.application.event.outbox.OutboxEvent
import dev.codeswamp.core.common.event.Event
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("article_outbox")
data class OutboxEventEntity (
    @Id
    val id: Long,

    @Column("event_type")
    val eventType: String,

    @Column("payload_json")
    val payloadJson: String,

    val status: String,

    @Column("created_at")
    val createdAt: Instant,

    @Column("retry_count")
    val retryCount: Int,
) {
    companion object {
        fun from(outboxEvent: OutboxEvent) = OutboxEventEntity(
            id = outboxEvent.id,
            eventType = outboxEvent.eventType,
            payloadJson = jacksonObjectMapper().writeValueAsString(outboxEvent.payload),
            status = outboxEvent.status.toString(),
            createdAt = outboxEvent.createdAt,
            retryCount = outboxEvent.retryCount
        )
    }

    fun toOutboxEvent() : OutboxEvent {
        val clazz = EventTypeRegistry.getClassFor(eventType)

        return OutboxEvent(
            id = id,
            eventType = eventType,
            payload = jacksonObjectMapper().readValue(payloadJson, clazz) as Event,
            status = OutboxEvent.EventStatus.valueOf(status),
            createdAt = createdAt,
            retryCount = retryCount
        )
    }
}