package dev.codeswamp.infrastructure.messaging

interface EventTranslator<I, O> {
    fun translate(event: I):  O
}