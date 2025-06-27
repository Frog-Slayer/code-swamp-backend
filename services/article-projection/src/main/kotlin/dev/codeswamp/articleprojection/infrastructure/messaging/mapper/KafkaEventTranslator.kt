package dev.codeswamp.articleprojection.infrastructure.messaging.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.articleprojection.application.event.event.ArticlePublishedEvent
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.infrakafka.KafkaEvent
import org.springframework.stereotype.Component

@Component
class KafkaEventTranslator(
    private val objectMapper: ObjectMapper
): EventTranslator<KafkaEvent, ApplicationEvent> {

    override fun translate(event: KafkaEvent): ApplicationEvent {
        return when (event.eventType) {
            "ArticlePublishedEvent" -> event.toArticlePublishedEvent()
            else -> throw IllegalArgumentException("Unsupported event type: ${event.eventType}")
        }
    }

    fun KafkaEvent.toArticlePublishedEvent(): ArticlePublishedEvent = objectMapper.treeToValue(event, ArticlePublishedEvent::class.java)
}
