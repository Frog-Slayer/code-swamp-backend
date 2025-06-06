package dev.codeswamp.core.article.presentation.dto.request

data class ArticleCreateRequestDto (
    val title: String,
    val content: String,
    val type: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val slug: String,
    val summary: String,
    val folderId: Long,
)