package dev.codeswamp.article.presentation.dto.response.article

import java.time.Instant

data class ArticleEditResponseDto(
    val id: Long,
    val title: String,
    val createdAt: Instant,
    val updatedAt: Instant,

    val isPublic: Boolean,
    val isPublished: Boolean,
)