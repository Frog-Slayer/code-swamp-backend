package dev.codeswamp.articleprojection.application.support

import dev.codeswamp.articleprojection.application.dto.command.IndexArticleCommand

interface SearchEngineIndexer {
    suspend fun index(article: IndexArticleCommand)
    suspend fun remove(articleId: Long)
}