package dev.codeswamp.framework.infrastructure.messaging.messaging.kafka

import dev.codeswamp.core.application.event.eventbus.EventListener
import dev.codeswamp.core.application.port.incoming.BusinessEventDispatcher
import dev.codeswamp.core.infrastructure.messaging.messaging.kafka.mapper.KafkaEventTranslator
import dev.codeswamp.infrakafka.KafkaEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.support.Acknowledgment

class KafkaEventListener(
    private val eventTranslator: KafkaEventTranslator,
    private val dispatcher: BusinessEventDispatcher,
) : EventListener {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun listen(
        event: KafkaEvent,
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