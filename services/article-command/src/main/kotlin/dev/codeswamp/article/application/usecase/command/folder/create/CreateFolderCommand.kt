package dev.codeswamp.article.application.usecase.command.folder.create

data class CreateFolderCommand (
    val userId: Long,
    val name: String,
    val parentId: Long
)

data class CreateRootFolderCommand (
    val userId: Long,
    val name: String,
)
