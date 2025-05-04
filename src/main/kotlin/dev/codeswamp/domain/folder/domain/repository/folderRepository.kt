package dev.codeswamp.domain.folder.domain.repository

import dev.codeswamp.domain.folder.domain.entity.Folder
import java.util.Optional

interface folderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)
    fun findById(folderId: Long): Optional<Folder>
}