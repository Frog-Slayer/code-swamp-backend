package dev.codeswamp.application.event

interface EventHandler<T: Any> {
    fun canHandle(event: Any):  Boolean
    suspend fun handle(event: T)
}