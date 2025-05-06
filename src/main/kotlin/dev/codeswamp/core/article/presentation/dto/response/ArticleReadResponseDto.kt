package dev.codeswamp.core.article.presentation.dto.response

import java.time.Instant

data class ArticleReadResponseDto (
    val id: Long,
    val title: String,
    val createdAt: Instant,
    val updatedAt: Instant,

    val isPublic: Boolean,
    val isPublished: Boolean,

    val content: String,
    //val emojis: List<Emoji>
    //val comments: List<Comment>
)