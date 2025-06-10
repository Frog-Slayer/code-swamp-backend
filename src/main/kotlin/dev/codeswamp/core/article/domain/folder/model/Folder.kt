package dev.codeswamp.core.article.domain.folder.model

import dev.codeswamp.core.article.domain.AggregateRoot
import dev.codeswamp.core.article.domain.ArticleDomainEvent
import dev.codeswamp.core.article.domain.folder.event.FolderPathChangedEvent
import org.springframework.security.access.AccessDeniedException

data class Folder private constructor(
    val id: Long,
    val ownerId: Long,//UserId?
    val name: Name,
    val fullPath: String,
    val parentId: Long? = null,//root = null -> 객체로?
) : AggregateRoot() {
    companion object {
        fun create(
            id: Long,
            ownerId: Long,
            name: String,
            parent: Folder,
            checkDuplicatedFolderName: (Long, String) -> Unit
        ) : Folder {
            val folderName = Name.of(name)//formant check
            checkDuplicatedFolderName(parent.id, name)

            return Folder(
                id,
                ownerId,
                folderName,
                fullPath = "${parent.fullPath}/$name",
                parentId = parent.id
            )
        }

        fun createRoot(
            id: Long,
            ownerId: Long,
            name: String,
        ): Folder {
            val folderName = Name.ofRoot("@$name")
            return Folder(
                id,
                ownerId,
                name = folderName,
                parentId = null,
                fullPath = folderName.value,
            )
        }

        fun from(
            id: Long,
            ownerId: Long,
            name: String,
            fullPath: String,
            parentId: Long?
        ) = Folder(
            id,
            ownerId,
            Name.of(name),
            fullPath,
            parentId
        )
    }

    fun rename(newName: String, checkDuplicatedFolderName: (Long, String) -> Unit) : Folder{
        parentId?.let{ checkDuplicatedFolderName(parentId, name.value) } ?: throw Exception("cannot rename root folder")

        val folderName = Name.of(newName)
        val parentPath = fullPath.substringBeforeLast("/")

        val newPath = "$parentPath/$newName"

        return this.copy(
            fullPath = newPath,
            name = folderName
        ).withEvent(FolderPathChangedEvent(
            id,
            fullPath,
            newPath
        ))
    }

    fun moveTo(newParent: Folder, checkDuplicatedFolderName: (Long, String) -> Unit) : Folder {
        if (newParent.id == parentId || newParent.id == id) return this
        if (isChild(newParent)) throw Exception("cannot move to descendants")
        checkDuplicatedFolderName(newParent.id, name.value)

        val newPath = "${newParent.fullPath}/$name"

        return this.copy(
                fullPath = newPath,
                parentId = newParent.id
            ).withEvent(FolderPathChangedEvent(
            id,
            fullPath,
            newPath
        ))
    }

    private fun isChild(newParent: Folder): Boolean {
        return newParent.fullPath.startsWith("$fullPath/")
    }

    fun checkOwnership(userId: Long) {
        if (ownerId != userId) throw AccessDeniedException("You are not the owner of this folder")
    }

    fun withEvent(event: ArticleDomainEvent) : Folder {
        val newFolder = this.copy()
        newFolder.addEvent(event)
        return newFolder
    }
}