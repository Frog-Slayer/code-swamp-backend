package dev.codeswamp.domain.article.domain.repository

import dev.codeswamp.domain.article.domain.model.Article

interface ArticleRepository {

    fun save(article: Article): Article
    fun delete(article: Article)

    fun findAllByIds(articleIds : List<Long>): List<Article>
    fun findById(articleId : Long): Article

    fun findAllByKeywords(keywords: List<String>): List<Article>
}