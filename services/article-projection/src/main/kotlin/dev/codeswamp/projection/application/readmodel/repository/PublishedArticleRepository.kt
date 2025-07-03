package dev.codeswamp.projection.application.readmodel.repository

import dev.codeswamp.projection.application.readmodel.model.PublishedArticle

interface PublishedArticleRepository {
    suspend fun findByArticleId(articleId: Long) : PublishedArticle?
    suspend fun save(article: PublishedArticle): PublishedArticle
    suspend fun deleteByArticleId(articleId: Long)
}