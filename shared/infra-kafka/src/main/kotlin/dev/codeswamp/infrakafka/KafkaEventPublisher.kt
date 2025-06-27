package dev.codeswamp.infrakafka

import kotlinx.coroutines.future.await
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, KafkaEvent>
) {
    suspend fun publish(event : KafkaEvent)  {
        val message: Message<KafkaEvent> = MessageBuilder
            .withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, event.topic)
            .setHeader(KafkaHeaders.KEY, event.key)
            .setHeader("eventType", event.eventType)
            .build()

        kafkaTemplate.send(message).await()
    }
}