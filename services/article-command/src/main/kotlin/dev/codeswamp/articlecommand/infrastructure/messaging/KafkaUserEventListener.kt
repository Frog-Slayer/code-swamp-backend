package dev.codeswamp.articlecommand.infrastructure.messaging

import dev.codeswamp.core.common.event.BusinessEvent
import dev.codeswamp.core.infrastructure.messaging.EventTranslator
import dev.codeswamp.framework.application.port.incoming.BusinessEventDispatcher
import dev.codeswamp.framework.infrastructure.messaging.kafka.KafkaEventListener
import dev.codeswamp.infrakafka.KafkaEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaUserEventListener(
    eventTranslator: EventTranslator<KafkaEvent, BusinessEvent>,
    dispatcher: BusinessEventDispatcher
) : KafkaEventListener(eventTranslator, dispatcher) {

    @KafkaListener(
        topics = ["user-service"],
        groupId = "article-command",
    )
    override suspend fun listen(event: KafkaEvent, ack: Acknowledgment) {
        super.listen(event, ack)
    }
}