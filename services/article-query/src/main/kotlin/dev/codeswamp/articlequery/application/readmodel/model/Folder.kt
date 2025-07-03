package dev.codeswamp.articlequery.application.readmodel.model

data class Folder private constructor(
    val id: Long,
    val ownerId: Long,
    val name: String,
    val fullPath: String,
    val parentId: Long? = null,//root = null -> 객체로?
) {
    companion object {
        fun from(
            id: Long,
            ownerId: Long,
            name: String,
            fullPath: String,
            parentId: Long?
        ) = Folder(
            id,
            ownerId,
            name,
            fullPath,
            parentId
        )
    }
}