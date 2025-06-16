package dev.codeswamp.core.article.application.usecase.command.folder.create

data class CreateFolderResult (
    val folderId: Long,
    val parentId: Long,
    val name: String,
)