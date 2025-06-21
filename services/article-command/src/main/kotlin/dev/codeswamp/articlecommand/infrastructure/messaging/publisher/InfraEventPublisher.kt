package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.core.common.event.EventPublisher
import dev.codeswamp.core.infrastructure.event.InfraEvent

interface InfraEventPublisher : EventPublisher<InfraEvent> {
    override fun publish(event: InfraEvent)
}