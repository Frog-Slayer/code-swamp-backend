package dev.codeswamp.infrastructure.messaging

import dev.codeswamp.articlecommand.infrastructure.event.event.InfraEvent

interface InfraEventPublisher {
    fun publish(event: InfraEvent)
}