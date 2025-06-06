package dev.codeswamp.core.article.application.dto.command

data class UpdateArticleCommand (
    val userId: Long?,
    val articleId: Long,
    val versionId: Long,
    val type: String,

    val title: String?,
    val diff: String?,
    val isPublic:  Boolean?,
    val thumbnailUrl: String?,
    val slug: String?,
    val summary: String?,
    val folderId: Long?
)