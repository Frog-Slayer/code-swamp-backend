package dev.codeswamp.articlequery.application.support

import dev.codeswamp.articlequery.application.dto.command.ArticleSearchDTO

interface SearchEngineReader {
    suspend fun search(query: ArticleSearchDTO): List<Long>
}