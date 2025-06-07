package dev.codeswamp.core.article.application.usecase.command.publish

data class PublishArticleCommand (
    val userId: Long,
    val articleId: Long?,
    val versionId: Long?,
    val title: String,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,

)