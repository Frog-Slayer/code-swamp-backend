package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.entity.FolderEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FolderR2dbcRepository : CoroutineCrudRepository<FolderEntity, Long> {
    suspend fun findAllByIdIsIn(folderIds: List<Long>): List<FolderEntity>
    suspend fun findByFullPath(fullPath: String): FolderEntity?
    suspend fun findAllByOwnerId(ownerId: Long): List<FolderEntity>
}