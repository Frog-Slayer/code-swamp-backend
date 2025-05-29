package dev.codeswamp.core.article.application.dto.command

data class ArticleWriteCommand (
    val userId: Long,
    val title: String,
    val content: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,
)
