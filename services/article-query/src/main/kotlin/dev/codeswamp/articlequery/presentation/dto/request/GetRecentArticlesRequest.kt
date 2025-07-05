package dev.codeswamp.articlequery.presentation.dto.request

import java.time.Instant

data class GetRecentArticlesRequest (
    val limit: Int,
    val lastArticleId: Long?,
    val lastCreatedAt: Instant?,
)