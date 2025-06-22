package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.infrastructure.exception.infrastructure.EventTransientException
import dev.codeswamp.articlecommand.application.port.outgoing.InternalEventPublisher
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.ApplicationEventKafkaMapper
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.DomainEventKafkaMapper
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.InfraEventKafkaMapper
import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.core.domain.DomainEvent
import dev.codeswamp.core.infrastructure.event.InfraEvent
import dev.codeswamp.infrakafka.publisher.KafkaEventPublisher
import org.apache.kafka.common.errors.RetriableException
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class KafkaInternalEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : InternalEventPublisher {

    override suspend fun publish(event: Event) {
        try {
            val kafkaEvent = when (event) {
                is DomainEvent -> DomainEventKafkaMapper.translate(event)
                is InfraEvent -> InfraEventKafkaMapper.translate(event)
                is ApplicationEvent -> ApplicationEventKafkaMapper.translate(event)
                else -> throw IllegalArgumentException("Unsupported event type: $event")
            }

            kafkaPublisher.publish("article-command", kafkaEvent.key, kafkaEvent)
        } catch (e: RetriableException) {
            throw EventTransientException()//TODO
        } catch (e: Exception){
            throw e
        }
    }
}