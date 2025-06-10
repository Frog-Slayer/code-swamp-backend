package dev.codeswamp.core.article.domain.folder.repository

import dev.codeswamp.core.article.domain.folder.model.Folder

interface FolderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)
    fun findById(folderId: Long): Folder?
    fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String)
    fun findAllByIds(folderIds: List<Long>) : List<Folder>
    fun findFolderByFolderPath(folderPath: String): Folder?
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean
}