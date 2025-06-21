package dev.codeswamp.core.common.event

interface EventPublisher<T: Event> {
    fun publish(event: T)
}