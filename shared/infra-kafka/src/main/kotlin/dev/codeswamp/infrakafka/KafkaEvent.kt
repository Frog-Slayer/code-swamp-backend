package dev.codeswamp.infrakafka

import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant

data class KafkaEvent (
    val topic: String,
    val eventId: Long,
    val key: String?,
    val eventType: String,
    val occurredAt: Instant,
    val event: JsonNode
)