package dev.codeswamp.infrastructure.event

interface InfraEventHandler<T: Any> {
    fun canHandle(event: Any):  Boolean
    suspend fun handle(event: T)
}