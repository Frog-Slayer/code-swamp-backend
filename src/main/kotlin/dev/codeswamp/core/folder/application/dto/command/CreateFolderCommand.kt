package dev.codeswamp.core.folder.application.dto.command

data class CreateFolderCommand (
    val userId: Long,
    val name: String,
    val parentId: Long
)