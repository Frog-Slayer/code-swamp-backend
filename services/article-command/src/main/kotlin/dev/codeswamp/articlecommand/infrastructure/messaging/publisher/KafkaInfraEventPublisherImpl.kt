package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.infrastructure.event.event.InfraEvent
import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.InfraEventKafkaMapper
import dev.codeswamp.infrakafka.publisher.KafkaEventPublisher
import org.springframework.stereotype.Component

@Component
class KafkaInfraEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : InfraEventPublisher {

    override fun publish(event: InfraEvent) {
        val kafkaEvent = InfraEventKafkaMapper.map(event)
        kafkaPublisher.publish("user-service", kafkaEvent.key, kafkaEvent)
    }
}