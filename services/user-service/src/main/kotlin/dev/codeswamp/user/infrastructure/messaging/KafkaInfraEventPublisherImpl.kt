package dev.codeswamp.user.infrastructure.messaging

import dev.codeswamp.infrakafka.KafkaEventPublisher
import dev.codeswamp.user.infrastructure.event.InfraEvent
import dev.codeswamp.user.infrastructure.messaging.mapper.InfraEventKafkaMapper
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