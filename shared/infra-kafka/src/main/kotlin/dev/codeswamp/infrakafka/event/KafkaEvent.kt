package dev.codeswamp.infrakafka.event

import java.time.Instant

interface KafkaEvent {
    val eventId: String
    val key: String
    val eventType: String
    val occurredAt: Instant
}