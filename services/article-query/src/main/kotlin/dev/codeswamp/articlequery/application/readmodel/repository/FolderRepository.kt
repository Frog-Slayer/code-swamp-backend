package dev.codeswamp.articlequery.application.readmodel.repository

import dev.codeswamp.articlequery.application.readmodel.model.Folder

interface FolderRepository {
    suspend fun findById(folderId: Long, fields: Set<String>): Folder?
    suspend fun findAllByIds(folderIds: List<Long>, fields: Set<String>): List<Folder>
    suspend fun findFolderByFolderPath(folderPath: String, fields: Set<String>): Folder?
    suspend fun findAllByOwnerId(userId: Long, fields: Set<String>): List<Folder>
}