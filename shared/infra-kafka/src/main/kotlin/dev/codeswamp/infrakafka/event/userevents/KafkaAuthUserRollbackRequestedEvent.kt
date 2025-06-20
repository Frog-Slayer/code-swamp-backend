package dev.codeswamp.infrakafka.event.userevents

import dev.codeswamp.infrakafka.event.KafkaEvent
import java.time.Instant

data class KafkaAuthUserRollbackRequestedEvent(
    override val eventType: String = "auth_user_rollback",
    override val key: String,
    override val occurredAt: Instant,
    val failedUserEmail: String
):KafkaEvent
