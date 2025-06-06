package dev.codeswamp.core.article.application.usecase.command.create

data class CreateArticleCommand (
    val userId: Long,
    val type: String,
    val title: String,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,
)