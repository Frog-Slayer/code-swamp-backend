package dev.codeswamp.core.article.application.usecase.command.folder.delete

data class DeleteFolderCommand (
    val userId: Long,
    val folderId: Long,
)