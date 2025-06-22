package dev.codeswamp.article.domain.folder.repository

import dev.codeswamp.article.domain.folder.model.Folder

interface FolderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)
    fun findById(folderId: Long): Folder?
    fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String)

    fun findAllDescendantIdsByFolder(folder: Folder): List<Long>

    fun deleteAllById(folderIds: List<Long>)
    fun findAllByIds(folderIds: List<Long>): List<Folder>
    fun findFolderByFolderPath(folderPath: String): Folder?
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean

    fun findAllByOwnerId(userId: Long): List<Folder>
}