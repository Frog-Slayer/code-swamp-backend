package dev.codeswamp.core.folder.application.dto.command

data class CreateFolderCommand (
    val userId: Long,
    val name: String = "root",
    val parentId: Long? = null,
)