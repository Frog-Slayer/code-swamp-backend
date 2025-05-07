package dev.codeswamp.core.folder.domain.repository

import dev.codeswamp.core.folder.domain.entity.Folder
import java.util.Optional

interface FolderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)

    fun findById(folderId: Long): Optional<Folder>
    fun findAllByIds(folderIds: List<Long>) : List<Folder>
}