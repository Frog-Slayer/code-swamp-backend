package dev.codeswamp.article.application.usecase.command.article.publish

data class UpdatePublishCommand (
    val userId: Long,
    val articleId: Long,
    val versionId: Long,
    val title: String,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,

)

data class CreatePublishCommand (
    val userId: Long,
    val title: String,
    val diff: String,
    val isPublic: Boolean,
    val thumbnailUrl: String?,
    val folderId: Long,
    val slug: String,
    val summary: String,
)