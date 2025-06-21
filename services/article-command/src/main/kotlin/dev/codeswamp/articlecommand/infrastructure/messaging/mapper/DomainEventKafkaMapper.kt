package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.articlecommand.domain.DomainEvent
import dev.codeswamp.infrakafka.event.KafkaEvent

object DomainEventKafkaMapper {
    fun map(event: DomainEvent): KafkaEvent = when (event) {
        else -> throw IllegalArgumentException("Unsupported domain event type: $event")
    }
}
