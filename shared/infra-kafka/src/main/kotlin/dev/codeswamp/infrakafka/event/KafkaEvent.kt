package dev.codeswamp.infrakafka.event

import java.time.Instant

interface KafkaEvent {
    val eventType: String
    val occurredAt: Instant
}