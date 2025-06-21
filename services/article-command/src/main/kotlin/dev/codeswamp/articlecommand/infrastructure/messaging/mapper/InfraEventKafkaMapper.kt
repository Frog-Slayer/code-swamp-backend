package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.core.infrastructure.event.InfraEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.event.KafkaEvent

object InfraEventKafkaMapper : EventTranslator<InfraEvent, KafkaEvent> {
    override fun translate(event: InfraEvent): KafkaEvent {
        TODO("Not yet implemented")
    }
}