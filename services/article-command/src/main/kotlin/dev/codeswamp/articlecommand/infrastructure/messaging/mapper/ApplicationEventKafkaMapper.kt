package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.domain.DomainEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.event.KafkaEvent

object ApplicationEventKafkaMapper : EventTranslator<ApplicationEvent, KafkaEvent> {
    override fun translate(event: ApplicationEvent): KafkaEvent  = when (event) {
        else -> throw IllegalArgumentException("Unsupported application event type: $event")
    }
}
