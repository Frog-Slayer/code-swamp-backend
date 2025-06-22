package dev.codeswamp.articlecommand.domain.folder.repository

import dev.codeswamp.articlecommand.domain.folder.model.Folder

interface FolderRepository {
    suspend fun create(folder: Folder): Folder
    suspend fun save(folder: Folder): Folder
    suspend fun delete(folder: Folder)
    suspend fun findById(folderId: Long): Folder?
    suspend fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String)

    suspend fun findAllDescendantIdsByFolder(folder: Folder): List<Long>

    suspend fun deleteAllById(folderIds: List<Long>)
    suspend fun findAllByIds(folderIds: List<Long>): List<Folder>
    suspend fun findFolderByFolderPath(folderPath: String): Folder?
    suspend fun existsByParentIdAndName(parentId: Long, name: String): Boolean

    suspend fun findAllByOwnerId(userId: Long): List<Folder>
}