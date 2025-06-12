package dev.codeswamp.core.article.infrastructure.persistence.search.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MeiliSearchConfig {

    private val baseUrl = "http://localhost:7700"
    private val apikey = "Uyflv5mWWF3Apyp23PhvAPyFpVnJB6CC_coToMPFcrk" //TODO

    @Bean
    fun meiliWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", "Bearer $apikey")
            .build()
    }
}