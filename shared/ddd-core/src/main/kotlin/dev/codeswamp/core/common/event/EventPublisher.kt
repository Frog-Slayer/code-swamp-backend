package dev.codeswamp.core.common.event

interface EventPublisher<T: Event> {
    suspend fun publish(event: T)
}