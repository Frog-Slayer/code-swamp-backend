package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import dev.codeswamp.articlecommand.application.exception.infra.TransientEventException
import dev.codeswamp.articlecommand.application.port.outgoing.OutboxEventPublisher
import dev.codeswamp.core.application.event.outbox.OutboxEvent
import dev.codeswamp.infrakafka.KafkaEvent
import dev.codeswamp.infrakafka.KafkaEventPublisher
import org.apache.kafka.common.errors.RetriableException
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class KafkaOutboxEventPublisherImpl(
    private val objectMapper: ObjectMapper,
    private val kafkaPublisher: KafkaEventPublisher,
) : OutboxEventPublisher {

    override suspend fun publish(event: OutboxEvent) {
        try {
            val kafkaEvent = KafkaEvent(
                topic = event.serviceName,
                eventId = event.id,
                key = event.key,
                eventType = event.eventType,
                occurredAt = event.createdAt,
                event = objectMapper.valueToTree(event.payload),
            )

            kafkaPublisher.publish(kafkaEvent)
        } catch (e: RetriableException) {
            throw TransientEventException(e.message ?: e.javaClass.simpleName)
        } catch (e: Exception){
            throw e
        }
    }
}