package dev.codeswamp.core.article.application.usecase.create

data class CreateArticleCommand (
    val userId: Long,
    val type: String,
    val title: String,
    val content: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,
)