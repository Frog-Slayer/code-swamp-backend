package dev.codeswamp.core.infrastructure.persistence

interface TransactionExecutor {
    suspend fun <T> execute(block: suspend () -> T) : T
}