package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.application.exception.infra.TransientEventException
import dev.codeswamp.articlecommand.application.port.outgoing.OutboxEventPublisher
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.ApplicationEventKafkaMapper
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.DomainEventKafkaMapper
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.application.event.outbox.OutboxEvent
import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.domain.DomainEvent
import dev.codeswamp.infrakafka.publisher.KafkaEventPublisher
import org.apache.kafka.common.errors.RetriableException
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class KafkaOutboxEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : OutboxEventPublisher {

    override suspend fun publish(event: OutboxEvent) {
        try {
            val payload = when (event.payload) {
                is DomainEvent -> DomainEventKafkaMapper.translate(event.payload as DomainEvent)
                is ApplicationEvent -> ApplicationEventKafkaMapper.translate(event.payload as ApplicationEvent)
                else -> throw IllegalArgumentException("Unsupported event type: ${event.payload}")
            }

            kafkaPublisher.publish(event.serviceName, event.key, payload)

        } catch (e: RetriableException) {
            throw TransientEventException(e.message ?: e.javaClass.simpleName)
        } catch (e: Exception){
            throw e
        }
    }
}