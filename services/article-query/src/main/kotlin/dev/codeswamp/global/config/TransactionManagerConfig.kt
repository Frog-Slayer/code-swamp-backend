package dev.codeswamp.global.config

import jakarta.persistence.EntityManagerFactory
import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class TransactionManagerConfig {

    @Primary
    @Bean(name = ["transactionManager"])
    fun jpaTransactionManager(
        entityManagerFactory: EntityManagerFactory
    ): JpaTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}