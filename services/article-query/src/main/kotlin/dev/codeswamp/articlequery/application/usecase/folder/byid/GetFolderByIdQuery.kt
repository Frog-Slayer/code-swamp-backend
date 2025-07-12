package dev.codeswamp.articlequery.application.usecase.folder.byid

data class GetFolderByIdQuery(
    val folderId: Long,
    val fields: Set<String>
)