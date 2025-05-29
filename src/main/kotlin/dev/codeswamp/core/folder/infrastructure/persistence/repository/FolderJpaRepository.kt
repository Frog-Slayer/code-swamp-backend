package dev.codeswamp.core.folder.infrastructure.persistence.repository

import dev.codeswamp.core.folder.infrastructure.persistence.entity.FolderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FolderJpaRepository : JpaRepository<FolderEntity, Long> {
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean
}