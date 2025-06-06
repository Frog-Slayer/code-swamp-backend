package dev.codeswamp.core.article.application.dto.command

data class CreateRootFolderCommand (
    val userId: Long,
    val name: String,
)