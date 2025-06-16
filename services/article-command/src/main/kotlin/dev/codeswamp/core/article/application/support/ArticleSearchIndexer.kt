package dev.codeswamp.core.article.application.support

import dev.codeswamp.core.article.application.dto.command.ArticleIndexDTO
import dev.codeswamp.core.article.application.dto.command.ArticleSearchDTO

interface ArticleSearchIndexer {
    fun index(article: ArticleIndexDTO)
    fun remove(articleId: Long)
    fun search(query: ArticleSearchDTO): List<Long>
}