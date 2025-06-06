package dev.codeswamp.core.article.application.dto.command

data class ArticleUpdateCommand(
    val userId: Long,
    val articleId: Long,
    val versionId: Long,
    val type: String,
    val title: String,
    val content: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,
)
