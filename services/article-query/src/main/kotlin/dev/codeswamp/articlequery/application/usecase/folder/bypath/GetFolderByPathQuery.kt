package dev.codeswamp.articlequery.application.usecase.folder.bypath

data class GetFolderByPathQuery(
    val path: String,
    val fields: Set<String>
)