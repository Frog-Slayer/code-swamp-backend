package dev.codeswamp.articlequery.infrastructure.graphql

import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphQlConfig {
    @Bean
    fun runtimeWiringConfigurer(): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { wiringBuilder ->
            wiringBuilder.scalar(ExtendedScalars.DateTime)
            wiringBuilder.scalar(ExtendedScalars.GraphQLLong)
        }
    }
}