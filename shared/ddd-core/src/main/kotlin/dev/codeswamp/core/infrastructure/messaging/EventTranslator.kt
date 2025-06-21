package dev.codeswamp.core.infrastructure.messaging

interface EventTranslator<I, O> {
    fun translate(event: I):  O
}