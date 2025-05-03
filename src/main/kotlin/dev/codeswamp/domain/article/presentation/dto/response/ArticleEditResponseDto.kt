package dev.codeswamp.domain.article.presentation.dto.response

import java.time.Instant

data class ArticleEditResponseDto (
    val id: Long,
    val title: String,
    val createdAt: Instant,
    val updatedAt: Instant,

    val isPublic: Boolean,
    val isPublished: Boolean,

    //val contentsList: List<ArticleVersionsDto>,
    //val emojis: List<Emoji>
    //val comments: List<Comment>
)