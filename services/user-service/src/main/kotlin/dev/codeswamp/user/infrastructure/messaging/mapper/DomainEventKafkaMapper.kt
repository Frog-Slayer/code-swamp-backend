package dev.codeswamp.user.infrastructure.messaging.mapper

import dev.codeswamp.infrakafka.KafkaEvent
import dev.codeswamp.infrakafka.event.userevents.KafkaUserRegisteredEvent
import dev.codeswamp.user.domain.user.DomainEvent
import dev.codeswamp.user.domain.user.event.UserRegisteredEvent
import java.time.Instant

object DomainEventKafkaMapper {
    fun map(event: DomainEvent): KafkaEvent = when (event) {
        is UserRegisteredEvent -> event.toKafkaEvent()
        else -> throw IllegalArgumentException("Unsupported domain event type: $event")
    }
}

fun UserRegisteredEvent.toKafkaEvent() = KafkaUserRegisteredEvent(
    occurredAt = Instant.now(),
    key = userId.toString(),
    userId = userId,
    username = username,
)