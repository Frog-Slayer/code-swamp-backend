package dev.codeswamp.core.application.event

import dev.codeswamp.core.common.event.Event

interface EventPublisher<T: Event> {
    suspend fun publish(event: T)
}