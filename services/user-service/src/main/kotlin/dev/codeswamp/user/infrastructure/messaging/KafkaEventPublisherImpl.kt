package dev.codeswamp.user.infrastructure.messaging

import dev.codeswamp.infrakafka.KafkaEventPublisher
import dev.codeswamp.user.application.event.ApplicationEvent
import dev.codeswamp.user.application.port.outgoing.messaging.EventPublisher
import dev.codeswamp.user.domain.user.DomainEvent
import dev.codeswamp.user.infrastructure.messaging.mapper.ApplicationEventKafkaMapper
import dev.codeswamp.user.infrastructure.messaging.mapper.DomainEventKafkaMapper
import org.springframework.stereotype.Component

@Component
class KafkaEventPublisherImpl(
    private val kafkaPublisher: KafkaEventPublisher,
) : EventPublisher {

    override fun publish(event: DomainEvent) {
        val kafkaEvent = DomainEventKafkaMapper.map(event)
        kafkaPublisher.publish("user-service", kafkaEvent.key, kafkaEvent)
    }

    override fun publish(event: ApplicationEvent) {
        val kafkaEvent = ApplicationEventKafkaMapper.map(event)
        kafkaPublisher.publish("user-service", kafkaEvent.key, kafkaEvent)
    }

}