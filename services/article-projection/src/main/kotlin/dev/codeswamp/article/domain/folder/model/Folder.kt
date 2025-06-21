package dev.codeswamp.article.domain.folder.model

import dev.codeswamp.article.domain.AggregateRoot
import dev.codeswamp.article.domain.ArticleDomainEvent
import dev.codeswamp.article.domain.folder.event.FolderDeletionEvent
import dev.codeswamp.article.domain.folder.event.FolderPathChangedEvent
import dev.codeswamp.article.domain.folder.exception.FolderMoveNotAllowedException
import dev.codeswamp.article.domain.folder.exception.ForbiddenFolderAccessException
import dev.codeswamp.article.domain.folder.exception.RootFolderRenameException
import java.time.Instant

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
            checkDuplicatedFolderName: (Long, Name) -> Unit
        ): Folder {
            val folderName = Name.of(name)//format check
            checkDuplicatedFolderName(parent.id, folderName)

            return Folder(
                id,
                ownerId,
                folderName,
                fullPath = "${parent.fullPath}/${folderName.value}",
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
            Name.from(name),
            fullPath,
            parentId
        )
    }

    fun rename(newName: String, checkDuplicatedFolderName: (Long, Name) -> Unit): Folder {
        val newFolderName = Name.of(newName)
        parentId?.let { checkDuplicatedFolderName(parentId, newFolderName) } ?: throw RootFolderRenameException(name.value)

        val parentPath = fullPath.substringBeforeLast("/")
        val newPath = "$parentPath/${newFolderName.value}"

        return this.copy(
            fullPath = newPath,
            name = newFolderName
        ).withEvent(
            FolderPathChangedEvent(
                id,
                fullPath,
                newPath
            )
        )
    }

    fun moveTo(newParent: Folder, checkDuplicatedFolderName: (Long, Name) -> Unit): Folder {
        if (newParent.id == parentId || newParent.id == id) return this
        if (isChild(newParent)) throw FolderMoveNotAllowedException(fullPath, newParent.fullPath)
        checkDuplicatedFolderName(newParent.id, name)

        val newPath = "${newParent.fullPath}/${name.value}"

        return this.copy(
            fullPath = newPath,
            parentId = newParent.id
        ).withEvent(
            FolderPathChangedEvent(
                id,
                fullPath,
                newPath
            )
        )
    }

    fun markAsDeleted(folderIdsToDelete: List<Long>, deletedAt: Instant): Folder {
        return this.withEvent(
            FolderDeletionEvent(
                id,
                folderIdsToDelete,
                deletedAt
            )
        )
    }

    private fun isChild(newParent: Folder): Boolean {
        return newParent.fullPath.startsWith("$fullPath/")
    }

    fun checkOwnership(userId: Long) {
        if (ownerId != userId) throw ForbiddenFolderAccessException(id)
    }

    fun withEvent(event: ArticleDomainEvent): Folder {
        val newFolder = this.copy()
        newFolder.addEvent(event)
        return newFolder
    }

    fun isRoot(): Boolean = parentId == null
}