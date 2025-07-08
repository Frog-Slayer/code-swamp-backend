package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.databasequery.ColumnName

data class FolderEntity(
    @ColumnName("id")
    val id: Long?,

    @ColumnName("name")
    var name: String?,

    @ColumnName("owner_id")
    var ownerId: Long?,

    @ColumnName("parent_id")
    var parentId: Long? = null,

    @ColumnName("full_path")
    var fullPath: String?,
) {
    fun toDomain() = Folder.Companion.from(
        id = id,
        name = name,
        ownerId = ownerId,
        parentId = parentId,
        fullPath = fullPath
    )
}