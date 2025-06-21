package dev.codeswamp.articlecommand.application.port.outgoing

import dev.codeswamp.articlecommand.domain.DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)
}