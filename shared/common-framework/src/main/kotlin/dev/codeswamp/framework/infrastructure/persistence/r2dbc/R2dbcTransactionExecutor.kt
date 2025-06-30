package dev.codeswamp.framework.infrastructure.persistence.r2dbc

import dev.codeswamp.core.infrastructure.persistence.TransactionExecutor
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.transaction.reactive.TransactionalOperator

class R2dbcTransactionExecutor(
    private val transactionalOperator: TransactionalOperator,
) : TransactionExecutor {
    override suspend fun <T> execute(block: suspend () -> T): T {
        return transactionalOperator.execute {
            mono { block() }
        }.awaitSingle()
    }
}