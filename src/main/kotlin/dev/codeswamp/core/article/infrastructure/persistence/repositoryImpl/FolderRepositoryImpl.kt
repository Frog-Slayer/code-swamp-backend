package dev.codeswamp.core.article.infrastructure.persistence.repositoryImpl

import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import dev.codeswamp.core.article.infrastructure.persistence.jpa.entity.FolderEntity
import dev.codeswamp.core.article.infrastructure.persistence.jpa.repository.FolderJpaRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val folderJpaRepository: FolderJpaRepository,
    @PersistenceContext private val entityManager: EntityManager
) : FolderRepository {

    override fun save(folder: Folder): Folder {
        val parent = folder.parentId?.let{folderJpaRepository.findByIdOrNull(folder.parentId)}
        val saved = folderJpaRepository.save(FolderEntity.Companion.from(folder, parent)).toDomain()
        return saved
    }

    //TODO: bulk로 하위 폴더 전부 삭제
    override fun delete(folder: Folder) {
        folderJpaRepository.deleteById(folder.id)
    }

    override fun findById(folderId: Long): Folder? {
        return folderJpaRepository.findByIdOrNull(folderId)?.toDomain()
    }

    override fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String) {
        folderJpaRepository.bulkUpdateFullPath(oldFullPath, newFullPath, "$oldFullPath/%")
        entityManager.clear()
    }

    override fun findAllDescendantIdsByFolder(folder: Folder): List<Long> {
        return folderJpaRepository.findAllDescendantIdsByFolderFullPath("${folder.fullPath}/%")
    }

    override fun deleteAllById(folderIds: List<Long>) {
        folderJpaRepository.deleteAllById(folderIds)
    }

    override fun findAllByIds(folderIds: List<Long>): List<Folder> {
        return folderJpaRepository.findAllById(folderIds).map { it.toDomain() }
    }

    override fun findFolderByFolderPath(folderPath: String): Folder? {
        return folderJpaRepository.findByFullPath(folderPath)?.toDomain()
    }

    override fun existsByParentIdAndName(parentId: Long, name: String): Boolean {
       return folderJpaRepository.existsByParentIdAndName(parentId, name)
    }
}