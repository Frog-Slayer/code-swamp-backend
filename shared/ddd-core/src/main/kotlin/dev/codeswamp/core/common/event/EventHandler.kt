package dev.codeswamp.core.common.event

interface EventHandler<T: Event> {
    fun canHandle(event: Event):  Boolean
    suspend fun handle(event: T)
}