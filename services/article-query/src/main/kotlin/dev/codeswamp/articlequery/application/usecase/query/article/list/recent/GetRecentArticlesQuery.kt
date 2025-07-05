package dev.codeswamp.articlequery.application.usecase.query.article.list.recent

import java.time.Instant

data class GetRecentArticlesQuery (
    val userId: Long? = null,
    val lastCreatedAt: Instant? = null,
    val lastArticleId: Long? = null,
    val limit: Int,
)