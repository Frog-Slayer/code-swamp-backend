package dev.codeswamp.core.article.presentation.dto.request

data class ArticleWriteRequestDto (
    val title: String,
    val content: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,
)