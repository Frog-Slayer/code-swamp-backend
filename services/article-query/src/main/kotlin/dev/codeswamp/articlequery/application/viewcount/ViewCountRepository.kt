package dev.codeswamp.articlequery.application.viewcount

import dev.codeswamp.articlequery.application.context.Viewer

interface ViewCountRepository {
    suspend fun existsByArticleIdAndViewer(articleId: Long, viewer: Viewer) : Boolean
    suspend fun increment(articleId: Long, viewer: Viewer)
    suspend fun getTotalViews(articleId: Long): Int
    suspend fun getAllTotalViewsOfArticles(articleIds: List<Long>): List<Pair<Long, Int>>
    suspend fun clearViewers(articleId: Long)
}