package dev.codeswamp.articlequery.application.usecase.article.recent

import dev.codeswamp.articlequery.application.readmodel.model.PublishedArticle

interface GetRecentArticlesUseCase {
    suspend fun handle(query: GetRecentArticlesQuery) : List<PublishedArticle>
}