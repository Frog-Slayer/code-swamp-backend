package dev.codeswamp.core.article.application.dto.command

data class DeleteFolderCommand (
    val userId: Long,
    val folderId: Long,
)