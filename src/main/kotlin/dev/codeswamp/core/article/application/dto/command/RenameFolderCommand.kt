package dev.codeswamp.core.article.application.dto.command

data class RenameFolderCommand (
    val userId: Long,
    val folderId: Long,
    val newName: String
)