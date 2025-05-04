package dev.codeswamp.domain.folder.domain.repository

import dev.codeswamp.domain.folder.domain.entity.Folder

interface folderRepository {
    fun save(folder: Folder): Folder
    fun delete(folder: Folder)
    fun findById(folderId: Long): Folder
}