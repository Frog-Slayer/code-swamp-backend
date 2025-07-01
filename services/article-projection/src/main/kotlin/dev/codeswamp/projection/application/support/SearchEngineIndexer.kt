package dev.codeswamp.projection.application.support

import dev.codeswamp.projection.application.dto.command.IndexArticleCommand

interface SearchEngineIndexer {
    suspend fun index(article: IndexArticleCommand)
    suspend fun remove(articleId: Long)
}