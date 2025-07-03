package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface FolderRepository {
    suspend fun findById(folderId: Long): Folder?
    suspend fun findAllByIds(folderIds: List<Long>): List<Folder>
    suspend fun findFolderByFolderPath(folderPath: String): Folder?
    suspend fun findAllByOwnerId(userId: Long): List<Folder>
}