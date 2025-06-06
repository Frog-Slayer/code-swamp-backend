package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.folder.model.Folder
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class FolderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    val ownerId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: FolderEntity? = null,
) {
    companion object {
        fun from(folder: Folder, parent: FolderEntity?) = FolderEntity(
            id = folder.id,
            name = folder.name,
            ownerId = folder.ownerId,
            parent = parent
        )
    }

    fun toDomain() = Folder(
        id = id,
        name = name,
        ownerId = ownerId,
        parentId = parent?.id
    )
}