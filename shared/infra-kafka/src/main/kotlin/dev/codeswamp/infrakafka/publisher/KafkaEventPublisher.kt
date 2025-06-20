package dev.codeswamp.infrakafka.publisher

import dev.codeswamp.infrakafka.event.KafkaEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, KafkaEvent>
) {
    fun publish(
        topic: String,
        key: String,
        event: KafkaEvent,
    ) {
        val message: Message<KafkaEvent> = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.KEY, key)
            .setHeader("eventType", event.eventType)
            .build()

        kafkaTemplate.send(message)
    }
}