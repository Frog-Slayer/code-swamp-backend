package dev.codeswamp.framework.infrastructure.messaging.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.framework.application.outbox.EventTypeRegistry
import dev.codeswamp.infrakafka.KafkaEvent

class KafkaEventTranslator(
    private val objectMapper: ObjectMapper,
    private val eventTypeRegistry: EventTypeRegistry
): EventTranslator<KafkaEvent, BusinessEvent> {

    override fun translate(event: KafkaEvent): BusinessEvent{
        val clazz = eventTypeRegistry.getClassFor(event.eventType)
        return objectMapper.treeToValue(event.event, clazz)
    }
}
