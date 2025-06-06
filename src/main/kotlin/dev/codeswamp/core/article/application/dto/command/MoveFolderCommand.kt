package dev.codeswamp.core.article.application.dto.command

data class MoveFolderCommand (
    val userId: Long,
    val folderId: Long,
    val parentId: Long
)
