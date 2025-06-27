package dev.codeswamp.articleprojection.application.readmodel.repository

import dev.codeswamp.articleprojection.application.readmodel.model.PublishedArticle

interface PublishedArticleRepository {
    suspend fun save(article: PublishedArticle): PublishedArticle
}