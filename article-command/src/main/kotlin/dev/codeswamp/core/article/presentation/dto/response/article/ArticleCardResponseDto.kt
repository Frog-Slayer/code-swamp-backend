package dev.codeswamp.core.article.presentation.dto.response.article

import java.time.Instant

data class ArticleCardResponseDto (
    val thumbnailUrl: String,
    val title: String,
    val summary: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)