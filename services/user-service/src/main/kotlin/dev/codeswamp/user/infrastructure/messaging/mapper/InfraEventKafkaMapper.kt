package dev.codeswamp.user.infrastructure.messaging.mapper

import dev.codeswamp.infrakafka.event.KafkaEvent
import dev.codeswamp.user.infrastructure.event.InfraEvent

object InfraEventKafkaMapper {
    fun map(event: InfraEvent): KafkaEvent {
        TODO("Not yet implemented")
    }
}