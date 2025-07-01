package dev.codeswamp.core.application.event.eventbus

import dev.codeswamp.core.common.event.Event

interface EventHandler<T: Event> {
    fun canHandle(event: Event):  Boolean
    suspend fun handle(event: T)
}