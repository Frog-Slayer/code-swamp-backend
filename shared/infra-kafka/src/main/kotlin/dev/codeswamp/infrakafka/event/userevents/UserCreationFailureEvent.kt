package dev.codeswamp.infrakafka.event.userevents

import dev.codeswamp.infrakafka.event.KafkaEvent
import java.time.Instant

data class KafkaUserCreationFailureEvent(
    override val eventType: String = "UserCreationFailure",
    override val occurredAt: Instant,
    val failedUserId: Long
) : KafkaEvent
