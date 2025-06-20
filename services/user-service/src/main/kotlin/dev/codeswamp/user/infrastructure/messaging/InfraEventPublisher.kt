package dev.codeswamp.user.infrastructure.messaging

import dev.codeswamp.user.infrastructure.event.InfraEvent

interface InfraEventPublisher {
    fun publish(event: InfraEvent)
}