package dev.codeswamp.projection.infrastructure.search

import dev.codeswamp.projection.application.dto.command.IndexArticleCommand
import dev.codeswamp.projection.application.support.SearchEngineIndexer
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Component
class MeiliSearchIndexer(
    @Qualifier("meiliWebClient") private val meiliClient: WebClient,
) : SearchEngineIndexer{

    @PostConstruct
    fun initialize() = runBlocking {
        try {
            createIndexIfNotExist("articles")
        } catch (e: Exception) {
            throw IllegalStateException("Failed to initialized MeiliSearch", e)
        }
    }

    suspend fun createIndexIfNotExist(indexName: String): String {
        return try {
             meiliClient.get()
                .uri("/indexes/$indexName")
                .retrieve()
                 .awaitBody<String>()
                 .let { "index $indexName already exists" }
        } catch (e : WebClientResponseException.NotFound) {
            createIndex(indexName)
            "index $indexName newly created"
        } catch (e : WebClientResponseException) {
            throw IllegalStateException("Failed to initialized MeiliSearch", e)
        }
    }

    suspend fun createIndex(indexName: String): String {
        return meiliClient.post()
            .uri("/indexes")
            .bodyValue(
                mapOf(
                    "uid" to indexName,
                    "primaryKey" to "articleId"
                )
            )
            .retrieve()
            .awaitBody<String>()
    }

    override suspend fun index(article: IndexArticleCommand) {
        meiliClient.post()
            .uri("/indexes/articles/documents")
            .bodyValue(article)
            .retrieve()
            .awaitBodyOrNull<String>()
            ?: throw IllegalStateException("Failed to index article")
    }

    override suspend fun remove(articleId: Long) {
        meiliClient.delete()
            .uri("/indexes/articles/documents/$articleId")
            .retrieve()
            .awaitBodyOrNull<String>()
            ?: throw IllegalStateException("Failed to remove article")
    }
}