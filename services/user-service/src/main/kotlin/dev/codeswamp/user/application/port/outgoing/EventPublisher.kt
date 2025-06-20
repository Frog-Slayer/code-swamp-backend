package dev.codeswamp.user.application.port.outgoing

import dev.codeswamp.user.application.event.ApplicationEvent
import dev.codeswamp.user.domain.user.DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)
    fun publish(event: ApplicationEvent)
}