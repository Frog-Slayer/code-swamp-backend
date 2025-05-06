package dev.codeswamp.core.article.presentation.dto.request

data class ArticleMetadataDto (
    val id: Long,
    val title: String,
    val isPublic: Boolean,
    val version: Int,
)