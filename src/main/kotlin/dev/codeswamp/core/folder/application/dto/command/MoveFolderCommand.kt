package dev.codeswamp.core.folder.application.dto.command

data class MoveFolderCommand (
    val userId: Long,
    val folderId: Long,
    val parentId: Long
)
