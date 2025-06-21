package dev.codeswamp.articlecommand.infrastructure.messaging.listener

import dev.codeswamp.articlecommand.application.port.incoming.MessageDispatcher
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.KafkaEventTranslator
import dev.codeswamp.infrakafka.event.KafkaEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UserEventListener(
    private val eventTranslator: KafkaEventTranslator,
    private val dispatcher: MessageDispatcher,
){

    @KafkaListener(
        topics = ["user-service"],
        groupId = "article-command-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun listen(
        @Payload event: KafkaEvent,
        ack: Acknowledgment
    ){
        try {
            val internalEvent = eventTranslator.translate(event)
            dispatcher.dispatch(internalEvent)
            ack.acknowledge()
        } catch (e : Exception) {
            //log & dlq & retry
        }
    }
}