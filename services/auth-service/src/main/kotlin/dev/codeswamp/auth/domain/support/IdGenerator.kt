package dev.codeswamp.auth.domain.support

interface IdGenerator {
    fun generateId(): Long
}