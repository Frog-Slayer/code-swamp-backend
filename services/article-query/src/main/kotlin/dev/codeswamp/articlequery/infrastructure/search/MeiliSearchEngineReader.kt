package dev.codeswamp.articlequery.infrastructure.search

import dev.codeswamp.articlequery.application.dto.command.ArticleSearchDTO
import dev.codeswamp.articlequery.application.support.SearchEngineReader
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class MeiliSearchEngineReader(
    @Qualifier("meiliWebClient") private val meiliClient: WebClient,
) : SearchEngineReader {

    override suspend fun search(query: ArticleSearchDTO): List<Long> {
        val response = meiliClient.get()
            .uri("/indexes/articles/search?q=${query.keyword}&attributesToRetrieve=articleId")
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingleOrNull()

        val hits = (response?.get("hits") as? List<*>)?.filterIsInstance<Map<String, Any>>() ?: emptyList()
        val articleIds = hits.mapNotNull { (it["articleId"] as? Number)?.toLong() }
        return articleIds
    }
}