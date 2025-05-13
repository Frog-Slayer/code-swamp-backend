package dev.codeswamp.core.article.domain.repository

import dev.codeswamp.core.article.domain.model.Article

interface ArticleRepository {
    fun save(article: Article): Article
    fun deleteById(id: Long)

    fun findAllByIds(articleIds : List<Long>): List<Article>
    fun findById(articleId : Long): Article?
}