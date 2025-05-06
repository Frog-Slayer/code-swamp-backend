package dev.codeswamp.core.article.infrastructure.search

interface ArticleSearchRepository {
    fun searchArticleIdsByKeywords(keywords: List<String>): List<Long>
}