package dev.codeswamp.domain.article.presentation.dto.request

data class ArticleMetadataDto (
    val id: Long,
    val title: String,
    val isPublic: Boolean,
    val version: Int,
)