package dev.codeswamp.core.infrastructure.messaging.messaging.kafka.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.KafkaEvent

class KafkaEventTranslator(
    private val objectMapper: ObjectMapper
): EventTranslator<KafkaEvent, ApplicationEvent> {

    override fun translate(event: KafkaEvent): ApplicationEvent {
        return when ( event.eventType ) {
            "UserRegisteredEvent" ->  event.toUserRegisteredEvent()
            else -> throw IllegalArgumentException("Unsupported event type: ${event.eventType}")
        }
    }

    fun KafkaEvent.toUserRegisteredEvent(): UserRegisteredEvent = objectMapper.treeToValue(this.event, UserRegisteredEvent::class.java)

}
