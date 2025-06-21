package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.application.port.outgoing.EventPublisher
import dev.codeswamp.articlecommand.domain.DomainEvent
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.DomainEventKafkaMapper
import dev.codeswamp.infrakafka.publisher.KafkaEventPublisher
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : EventPublisher {

    override fun publish(event: DomainEvent) {
        val kafkaEvent = DomainEventKafkaMapper.map(event)
        kafkaPublisher.publish("article-command", kafkaEvent.key, kafkaEvent)
    }

}