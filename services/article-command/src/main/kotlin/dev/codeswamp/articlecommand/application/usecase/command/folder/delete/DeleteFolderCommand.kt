package dev.codeswamp.articlecommand.application.usecase.command.folder.delete

data class DeleteFolderCommand(
    val userId: Long,
    val folderId: Long,
)