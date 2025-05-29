package dev.codeswamp.core.folder.domain.repository

import dev.codeswamp.core.folder.domain.entity.Folder
import java.util.Optional

interface FolderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)
    fun findById(folderId: Long): Folder?
    fun findAllByIds(folderIds: List<Long>) : List<Folder>
    fun findFolderByFullPath(rootName: String, paths: List<String>): Folder?
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean
}