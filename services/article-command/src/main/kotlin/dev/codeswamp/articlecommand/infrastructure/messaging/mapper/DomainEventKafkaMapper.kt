package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.core.domain.DomainEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.event.KafkaEvent

object DomainEventKafkaMapper : EventTranslator<DomainEvent, KafkaEvent> {
    override fun translate(event: DomainEvent): KafkaEvent  = when (event) {
        else -> throw IllegalArgumentException("Unsupported domain event type: $event")
    }
}
