package dev.codeswamp.articleprojection.infrastructure.persistence.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
@EnableTransactionManagement

class TransactionManagerConfig {
    @Primary
    @Bean(name = ["transactionManager"])
    fun r2dbcTransactionManager(factory: ConnectionFactory): R2dbcTransactionManager = R2dbcTransactionManager(factory)

    @Bean
    fun transactionalOperator(transactionManager: R2dbcTransactionManager): TransactionalOperator {
        return TransactionalOperator.create(transactionManager)
    }
}