package dev.codeswamp.articlecommand.application.usecase.command.folder.rename

data class RenameFolderCommand(
    val userId: Long,
    val folderId: Long,
    val newName: String
)