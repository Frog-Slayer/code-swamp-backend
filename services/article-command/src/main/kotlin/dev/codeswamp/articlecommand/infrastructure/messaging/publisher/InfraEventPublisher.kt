package dev.codeswamp.articlecommand.infrastructure.messaging.publisher

import dev.codeswamp.articlecommand.infrastructure.event.event.InfraEvent

interface InfraEventPublisher {
    fun publish(event: InfraEvent)
}