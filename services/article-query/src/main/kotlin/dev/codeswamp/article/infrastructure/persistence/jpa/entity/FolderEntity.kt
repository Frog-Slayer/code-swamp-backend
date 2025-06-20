package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.article.domain.folder.model.Folder
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class FolderEntity(
    @Id
    val id: Long,

    var name: String,

    var ownerId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: FolderEntity? = null,

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    var fullPath: String,
) {
    companion object {
        fun from(folder: Folder, parent: FolderEntity?) = FolderEntity(
            id = folder.id,
            name = folder.name.value,
            ownerId = folder.ownerId,
            parent = parent,
            fullPath = folder.fullPath
        )
    }

    fun toDomain() = Folder.Companion.from(
        id = id,
        name = name,
        ownerId = ownerId,
        parentId = parent?.id,
        fullPath = fullPath
    )

    fun update(name: String, parent: FolderEntity, fullPath: String) {
        this.name = name
        this.parent = parent
        this.fullPath = fullPath
    }
}