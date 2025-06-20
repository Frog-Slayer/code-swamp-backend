package dev.codeswamp.user.infrastructure.messaging.mapper

import dev.codeswamp.infrakafka.event.KafkaEvent
import dev.codeswamp.user.domain.user.DomainEvent

object DomainEventKafkaMapper {
    fun map(event: DomainEvent): KafkaEvent {
        TODO("Not yet implemented")
    }
}