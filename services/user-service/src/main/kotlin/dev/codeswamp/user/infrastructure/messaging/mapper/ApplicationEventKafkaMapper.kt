package dev.codeswamp.user.infrastructure.messaging.mapper

import dev.codeswamp.infrakafka.event.KafkaEvent
import dev.codeswamp.infrakafka.event.userevents.KafkaAuthUserRollbackRequestedEvent
import dev.codeswamp.user.application.event.ApplicationEvent
import dev.codeswamp.user.application.event.AuthUserRollbackRequestedEvent
import java.time.Instant

object ApplicationEventKafkaMapper {
    fun map(event: ApplicationEvent): KafkaEvent = when (event) {
        is AuthUserRollbackRequestedEvent -> KafkaAuthUserRollbackRequestedEvent(
            key = event.email,
            occurredAt = Instant.now(),
            failedUserEmail = event.email,
        )

        else -> throw IllegalArgumentException("Unsupported event: $event")
    }
}