package dev.codeswamp.core.article.application.readmodel.repository

import dev.codeswamp.core.article.application.readmodel.model.PublishedArticle

interface PublishedArticleRepository {
    fun save(publishedArticle: PublishedArticle) : PublishedArticle
    fun findByArticleId(articleId: Long) : PublishedArticle?
}