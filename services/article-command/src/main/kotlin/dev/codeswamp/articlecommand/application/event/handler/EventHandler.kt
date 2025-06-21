package dev.codeswamp.articlecommand.application.event.handler

interface EventHandler<T: Any> {
    fun canHandle(event: Any):  Boolean
    suspend fun handle(event: T)
}