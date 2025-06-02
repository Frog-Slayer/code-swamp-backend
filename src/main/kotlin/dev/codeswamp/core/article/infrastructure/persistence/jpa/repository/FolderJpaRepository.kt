package dev.codeswamp.core.article.infrastructure.persistence.jpa.repository

import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.FolderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FolderJpaRepository : JpaRepository<FolderEntity, Long> {
    fun existsByParentIdAndName(parentId: Long, name: String): Boolean
}