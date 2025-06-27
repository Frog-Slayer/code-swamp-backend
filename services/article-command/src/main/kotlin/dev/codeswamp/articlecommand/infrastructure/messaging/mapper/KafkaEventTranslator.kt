package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.articlecommand.application.event.event.UserRegisteredEvent
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.KafkaEvent
import org.springframework.stereotype.Component

@Component
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
