package dev.codeswamp.article.application.usecase.command.article.draft

data class UpdateDraftCommand (
    val userId: Long,
    val articleId: Long,
    val versionId: Long,
    val title: String,
    val diff: String,
    val folderId: Long,
)

data class CreateDraftCommand (
    val userId: Long,
    val title: String,
    val diff: String,
    val folderId: Long,
)