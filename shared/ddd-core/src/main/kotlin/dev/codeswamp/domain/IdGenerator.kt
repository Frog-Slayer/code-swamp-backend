package dev.codeswamp.domain

interface IdGenerator {
    fun generateId(): Long
}