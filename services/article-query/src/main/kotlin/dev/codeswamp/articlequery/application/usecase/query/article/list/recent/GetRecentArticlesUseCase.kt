package dev.codeswamp.articlequery.application.usecase.query.article.list.recent

import dev.codeswamp.articlequery.application.usecase.query.article.list.ArticleListItem

interface GetRecentArticlesUseCase {
    suspend fun getRecentArticles(query: GetRecentArticlesQuery) : List<ArticleListItem>
}