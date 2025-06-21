package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.infrastructure.messaging.mapper.InfraEventKafkaMapper
import dev.codeswamp.core.infrastructure.event.InfraEvent
import dev.codeswamp.infrakafka.publisher.KafkaEventPublisher
import org.springframework.stereotype.Component

@Component
class KafkaInfraEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : InfraEventPublisher {

    override fun publish(event: InfraEvent) {
        val kafkaEvent = InfraEventKafkaMapper.translate(event)
        kafkaPublisher.publish("article-command", kafkaEvent.key, kafkaEvent)
    }
}