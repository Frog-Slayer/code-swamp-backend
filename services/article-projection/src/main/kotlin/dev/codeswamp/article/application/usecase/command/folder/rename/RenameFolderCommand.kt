package dev.codeswamp.article.application.usecase.command.folder.rename

data class RenameFolderCommand(
    val userId: Long,
    val folderId: Long,
    val newName: String
)