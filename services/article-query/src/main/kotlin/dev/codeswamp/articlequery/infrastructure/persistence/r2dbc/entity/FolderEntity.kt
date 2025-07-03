package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("folder")
data class FolderEntity(
    @Id
    val id: Long,

    var name: String,

    @Column("owner_id")
    var ownerId: Long,

    @Column("parent_id")
    var parentId: Long? = null,

    @Column("full_path")
    var fullPath: String,
) {
    fun toDomain() = Folder.Companion.from(
        id = id,
        name = name,
        ownerId = ownerId,
        parentId = parentId,
        fullPath = fullPath
    )
}