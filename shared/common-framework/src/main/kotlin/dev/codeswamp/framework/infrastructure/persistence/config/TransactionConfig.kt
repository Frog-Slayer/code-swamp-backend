package dev.codeswamp.framework.infrastructure.persistence.config

import dev.codeswamp.core.infrastructure.persistence.TransactionExecutor
import dev.codeswamp.framework.infrastructure.persistence.r2dbc.R2dbcTransactionExecutor
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
class TransactionConfig {

    @Bean
    fun r2dbcTransactionManager(factory: ConnectionFactory): R2dbcTransactionManager = R2dbcTransactionManager(factory)

    @Bean
    fun transactionalOperator(transactionManager: R2dbcTransactionManager): TransactionalOperator {
        return TransactionalOperator.create(transactionManager)
    }

    @Bean
    fun transactionExecutor(transactionalOperator: TransactionalOperator) : TransactionExecutor = R2dbcTransactionExecutor(transactionalOperator)
}