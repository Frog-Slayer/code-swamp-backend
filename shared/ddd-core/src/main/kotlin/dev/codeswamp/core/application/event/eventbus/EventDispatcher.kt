package dev.codeswamp.core.application.event.eventbus

import dev.codeswamp.core.common.event.Event

interface EventDispatcher<T: Event> {
    suspend fun dispatch(event: T)
}