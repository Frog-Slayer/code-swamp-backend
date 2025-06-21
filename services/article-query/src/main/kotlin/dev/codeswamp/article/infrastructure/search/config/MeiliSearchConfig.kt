package dev.codeswamp.article.infrastructure.search.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MeiliSearchConfig(
    @Value("\${meilisearch.url}") private val meiliSearchUrl: String,
    @Value("\${meilisearch.api-key}") private val meiliSearchApiKey: String,
) {

    @Bean
    fun meiliWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(meiliSearchUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", "Bearer $meiliSearchApiKey")
            .build()
    }
}