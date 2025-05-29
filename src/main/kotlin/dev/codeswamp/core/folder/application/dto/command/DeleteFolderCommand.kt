package dev.codeswamp.core.folder.application.dto.command

data class DeleteFolderCommand (
    val userId: Long,
    val folderId: Long,
)