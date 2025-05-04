package dev.codeswamp.domain.article.infrastructure.search

interface ArticleSearchRepository {
    fun searchArticleIdsByKeywords(keywords: List<String>): List<Long>
}