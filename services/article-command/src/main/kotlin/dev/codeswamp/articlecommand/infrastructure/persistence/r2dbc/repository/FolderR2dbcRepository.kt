package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository

import dev.codeswamp.articlecommand.domain.folder.model.Name
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.FolderEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface FolderR2dbcRepository : CoroutineCrudRepository<FolderEntity, Long> {
    override suspend fun findById(id: Long): FolderEntity?
    suspend fun findAllByIdIsIn(ids: List<Long>): List<FolderEntity>

    @Query("""
        INSERT INTO folder (id, name, owner_id, parent_id, full_path)
        VALUES (:id, :name, :ownerId, :parentId, :fullPath)
        RETURNING *
    """)
    suspend fun insertFolder(
        @Param("id") id: Long,
        @Param("name") name: String,
        @Param("ownerId") ownerId: Long,
        @Param("parentId") parentId: Long?,
        @Param("fullPath") fullPath: String
    ): FolderEntity

    @Modifying
    @Query(
        """
        UPDATE FolderEntity f 
        SET f.fullPath = CONCAT(:newFullPath, SUBSTRING(f.fullPath, LENGTH(:oldFullPath) + 1))
        WHERE f.fullPath LIKE :pattern
    """
    )
    suspend fun bulkUpdateFullPath(
        @Param("oldFullPath") oldFullPath: String,
        @Param("newFullPath") newFullPath: String,
        @Param("pattern") pattern: String
    ): Int


    @Query(
        """
        SELECT f.id
        FROM FolderEntity f 
        WHERE f.fullPath LIKE :fullPathPattern
    """
    )
    suspend fun findAllDescendantIdsByFolderFullPath(@Param("fullPathPattern") fullPathPattern: String): List<Long>
    suspend fun existsByParentIdAndName(parentId: Long, name: String): Boolean
    suspend fun findByFullPath(fullPath: String): FolderEntity?
    suspend fun findAllByOwnerId(ownerId: Long): List<FolderEntity>
}