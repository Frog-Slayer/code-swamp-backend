package dev.codeswamp.infrakafka.event.userevents

import dev.codeswamp.infrakafka.event.KafkaEvent
import java.time.Instant

data class KafkaUserRegisteredEvent(
    override val eventId: String,
    override val eventType: String = "user_register",
    override val key: String,
    override val occurredAt: Instant,
    val userId: Long,
    val username: String,
):KafkaEvent
