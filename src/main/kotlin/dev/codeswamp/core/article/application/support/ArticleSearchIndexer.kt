package dev.codeswamp.core.article.application.support

import dev.codeswamp.core.article.application.dto.ArticleIndexDTO
import dev.codeswamp.core.article.application.dto.ArticleSearchDTO

interface ArticleSearchIndexer {
    fun index(article: ArticleIndexDTO)
    fun remove(articleId: Long)
    fun search(query: ArticleSearchDTO): List<Long>
}