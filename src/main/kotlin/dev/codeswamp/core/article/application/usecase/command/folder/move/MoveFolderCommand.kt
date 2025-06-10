package dev.codeswamp.core.article.application.usecase.command.folder.move

data class MoveFolderCommand (
    val userId: Long,
    val folderId: Long,
    val newParentId: Long
)
