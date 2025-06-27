package dev.codeswamp.articlecommand.infrastructure.messaging.listener

import dev.codeswamp.articlecommand.application.port.incoming.ApplicationEventDispatcher
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.KafkaEventTranslator
import dev.codeswamp.core.application.event.eventbus.EventListener
import dev.codeswamp.infrakafka.KafkaEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class KafkaUserEventListener(
    private val eventTranslator: KafkaEventTranslator,
    private val dispatcher: ApplicationEventDispatcher,
) : EventListener {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["user-service"],
        groupId = "article-command-group",
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