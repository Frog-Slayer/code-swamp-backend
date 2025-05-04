package dev.codeswamp.domain.folder.infrastructure.persistence.repository

import dev.codeswamp.domain.folder.infrastructure.persistence.entity.FolderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FolderJpaRepository : JpaRepository<FolderEntity, Long> {


}