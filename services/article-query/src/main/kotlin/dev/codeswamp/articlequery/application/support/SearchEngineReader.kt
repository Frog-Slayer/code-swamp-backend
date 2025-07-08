package dev.codeswamp.articlequery.application.support

interface SearchEngineReader {
    suspend fun search(query: String): List<Long>
}