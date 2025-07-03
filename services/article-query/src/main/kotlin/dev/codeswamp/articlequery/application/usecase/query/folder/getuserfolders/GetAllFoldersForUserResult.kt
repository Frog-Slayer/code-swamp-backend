package dev.codeswamp.articlequery.application.usecase.query.folder.getuserfolders

data class FolderInfo(
    val id: Long,
    val name: String,
    val parentId: Long?
)

data class GetAllFoldersForUserResult(
    val folders: List<FolderInfo>
)