package dev.codeswamp.articlequery.application.viewcount.service

import dev.codeswamp.articlequery.application.context.Viewer

interface ViewCountService {
    suspend fun shouldBeCounted(viewer: Viewer, articleId: Long) : Boolean
    suspend fun incrementIfNeeded(viewer: Viewer, articleId: Long)
    suspend fun getTotalViews(articleId: Long) : Int
    suspend fun getArticlesTotalViews(articleIds: List<Long>) : List<Pair<Long, Int>>
}

