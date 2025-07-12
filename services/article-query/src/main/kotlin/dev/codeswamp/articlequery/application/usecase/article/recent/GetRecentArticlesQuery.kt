package dev.codeswamp.articlequery.application.usecase.article.recent

import dev.codeswamp.databasequery.FieldSelection
import java.time.Instant

data class GetRecentArticlesQuery(
    val userId: Long?,
    val lastCreatedAt: Instant?,
    val lastArticleId: Long?,
    val limit: Int,
    val fields: Set<String>
)