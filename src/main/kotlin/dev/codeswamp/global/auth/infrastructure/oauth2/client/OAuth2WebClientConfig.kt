package dev.codeswamp.global.auth.infrastructure.oauth2.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class OAuth2WebClientConfig {

    @Bean
    fun githubWebClient() : WebClient {
        return WebClient.builder()
            .baseUrl("https://api.github.com")
            .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
            .build()
    }

}