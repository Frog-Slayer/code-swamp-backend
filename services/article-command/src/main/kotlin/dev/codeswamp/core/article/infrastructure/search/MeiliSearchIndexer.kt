package dev.codeswamp.core.article.infrastructure.search

import dev.codeswamp.core.article.application.dto.command.ArticleIndexDTO
import dev.codeswamp.core.article.application.dto.command.ArticleSearchDTO
import dev.codeswamp.core.article.application.support.ArticleSearchIndexer
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

@Component
class MeiliSearchIndexer(
    @Qualifier("meiliWebClient") private val meiliClient : WebClient,
) : ArticleSearchIndexer {

    @PostConstruct
    fun initialize() {
        createIndexIfNotExist("articles")
            .subscribe()
    }

    fun createIndexIfNotExist(indexName: String) : Mono<String> {
        return meiliClient.get()
            .uri("/indexes/$indexName")
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume(WebClientResponseException.NotFound::class.java) { _ ->
                createIndex(indexName).thenReturn("index $indexName newly created")
            }
            .onErrorResume { error->
                Mono.error(IllegalStateException("Unexpected error occurred while creating index: ${error.message}"))
            }
    }

    fun createIndex(indexName: String): Mono<String> {
         return meiliClient.post()
             .uri("/indexes")
             .bodyValue(mapOf(
                 "uid" to indexName,
                 "primaryKey" to "articleId"
             ))
             .retrieve()
             .bodyToMono(String::class.java)
    }

    // TODO 나중에 리액티브하게 변경
    override fun index(article: ArticleIndexDTO) {
        meiliClient.post()
            .uri("/indexes/articles/documents")
            .bodyValue(article)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
            ?: throw IllegalStateException("Failed to index article")
    }

    override fun remove(articleId: Long) {
         meiliClient.delete()
            .uri("/indexes/articles/documents/$articleId")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
            ?: throw IllegalStateException("Failed to remove article")
    }

    override fun search(query: ArticleSearchDTO): List<Long> {
        val response = meiliClient.get()
            .uri("/indexes/articles/search?q=${query.keyword}&attributesToRetrieve=articleId")
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()  // 동기적으로 처리

        val hits = (response?.get("hits") as? List<*>)?.filterIsInstance<Map<String, Any>>() ?: emptyList()
        val articleIds = hits.mapNotNull { (it["articleId"] as? Number)?.toLong() }
        return articleIds
    }
}