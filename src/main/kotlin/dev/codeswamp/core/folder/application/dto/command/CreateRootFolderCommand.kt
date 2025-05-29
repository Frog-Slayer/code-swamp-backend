package dev.codeswamp.core.folder.application.dto.command

data class CreateRootFolderCommand (
    val userId: Long,
    val name: String,
)