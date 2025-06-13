package dev.codeswamp

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig {
    @Bean
    fun routes(builder: RouteLocatorBuilder) : RouteLocator {
        return builder.routes()
            .route("auth-service") {
                it.path("/auth/**")
                    .uri("http://localhost:8080")
            }
            .route("user-service") {
                it.path("/user/**")
                    .uri("http://localhost:8081")
            }
            .build()

    }





}