package dev.codeswamp.articlecommand.infrastructure.messaging.mapper

import dev.codeswamp.articlecommand.application.event.event.UserRegisteredEvent
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.event.KafkaEvent
import dev.codeswamp.infrakafka.event.userevents.KafkaUserRegisteredEvent
import org.springframework.stereotype.Component

@Component
class KafkaEventTranslator : EventTranslator<KafkaEvent, ApplicationEvent> {

    override fun translate(event: KafkaEvent): ApplicationEvent {
        return when (event) {
            is KafkaUserRegisteredEvent -> event.toInternalEvent()
            else -> throw IllegalArgumentException("Unsupported event type: ${event.eventType}")
        }
    }

    fun KafkaUserRegisteredEvent.toInternalEvent() = UserRegisteredEvent(userId = userId, username = username)
}
