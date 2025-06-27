package dev.codeswamp.articleprojection.infrastructure.messaging.listener

import dev.codeswamp.articleprojection.infrastructure.messaging.mapper.KafkaEventTranslator
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.application.event.eventbus.EventListener
import dev.codeswamp.core.application.event.eventbus.EventDispatcher
import dev.codeswamp.infrakafka.KafkaEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class ArticleCommandEventListener(
    private val eventTranslator: KafkaEventTranslator,
    private val dispatcher: EventDispatcher<ApplicationEvent>,
) : EventListener {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["article-command"],
        groupId = "article-projection-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    suspend fun listen(
        @Payload event: KafkaEvent,
        ack: Acknowledgment
    ) {
        try {
            logger.info("listen event")
            val internalEvent = eventTranslator.translate(event)
            dispatcher.dispatch(internalEvent)
            ack.acknowledge()
        } catch (e:  IllegalArgumentException) {
            ack.acknowledge()
            throw e
        } catch (e: Exception) {
            //TODO
            //log & dlq & retry
        }
    }
}