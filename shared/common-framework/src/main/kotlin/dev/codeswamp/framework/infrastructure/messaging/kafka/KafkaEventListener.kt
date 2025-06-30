package dev.codeswamp.framework.infrastructure.messaging.kafka

import dev.codeswamp.core.application.event.eventbus.EventListener
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.framework.application.port.incoming.BusinessEventDispatcher
import dev.codeswamp.infrakafka.KafkaEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.support.Acknowledgment

class KafkaEventListener(
    private val eventTranslator: EventTranslator<KafkaEvent, BusinessEvent>,
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