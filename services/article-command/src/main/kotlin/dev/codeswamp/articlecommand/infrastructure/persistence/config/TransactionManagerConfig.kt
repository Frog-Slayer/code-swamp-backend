package dev.codeswamp.articlecommand.infrastructure.persistence.config

import io.r2dbc.spi.ConnectionFactory
import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(
    basePackages = ["dev.codeswamp.articlecommand.infrastructure.persistence.graph.repository"],
    transactionManagerRef = "neo4jTransactionManager"
)
class TransactionManagerConfig {

    @Primary
    @Bean(name = ["transactionManager"])
    fun r2dbcTransactionManager(factory: ConnectionFactory): R2dbcTransactionManager = R2dbcTransactionManager(factory)


    @Bean
    fun neo4jTransactionManager(driver: Driver): Neo4jTransactionManager = Neo4jTransactionManager(driver)

}