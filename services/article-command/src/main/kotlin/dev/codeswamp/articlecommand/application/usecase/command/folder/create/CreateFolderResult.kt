package dev.codeswamp.articlecommand.application.usecase.command.folder.create

data class CreateFolderResult(
    val folderId: Long,
    val parentId: Long,
    val name: String,
)