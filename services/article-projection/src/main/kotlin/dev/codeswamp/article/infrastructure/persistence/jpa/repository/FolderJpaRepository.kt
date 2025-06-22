package dev.codeswamp.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.article.infrastructure.persistence.jpa.entity.FolderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FolderJpaRepository : JpaRepository<FolderEntity, Long> {

    @Modifying
    @Query(
        """
        UPDATE FolderEntity f 
        SET f.fullPath = CONCAT(:newFullPath, SUBSTRING(f.fullPath, LENGTH(:oldFullPath) + 1))
        WHERE f.fullPath LIKE :pattern
    """
    )
    fun bulkUpdateFullPath(
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
    fun findAllDescendantIdsByFolderFullPath(@Param("fullPathPattern") fullPathPattern: String): List<Long>
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean
    fun findByFullPath(fullPath: String): FolderEntity?

    fun findAllByOwnerId(ownerId: Long): List<FolderEntity>
}