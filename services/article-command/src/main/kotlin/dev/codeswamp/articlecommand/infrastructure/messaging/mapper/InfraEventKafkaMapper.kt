package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.articlecommand.infrastructure.event.event.InfraEvent
import dev.codeswamp.infrakafka.event.KafkaEvent

object InfraEventKafkaMapper {
    fun map(event: InfraEvent): KafkaEvent {
        TODO("Not yet implemented")
    }
}