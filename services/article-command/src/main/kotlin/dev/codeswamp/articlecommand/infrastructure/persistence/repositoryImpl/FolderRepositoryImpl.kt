package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.folder.model.Folder
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.FolderEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.FolderJpaRepository
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val folderJpaRepository: FolderJpaRepository,
) : FolderRepository {

    override suspend fun save(folder: Folder): Folder {
        val parent = folder.parentId?.let { folderJpaRepository.findById(folder.parentId) }
        val saved = folderJpaRepository.save(FolderEntity.Companion.from(folder, parent)).toDomain()
        return saved
    }

    //TODO: bulk로 하위 폴더 전부 삭제
    override suspend fun delete(folder: Folder) {
        folderJpaRepository.deleteById(folder.id)
    }

    override suspend fun findById(folderId: Long): Folder? {
        return folderJpaRepository.findById(folderId)?.toDomain()
    }

    override suspend fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String) {
        folderJpaRepository.bulkUpdateFullPath(oldFullPath, newFullPath, "$oldFullPath/%")
    }

    override suspend fun findAllDescendantIdsByFolder(folder: Folder): List<Long> {
        return folderJpaRepository.findAllDescendantIdsByFolderFullPath("${folder.fullPath}/%")
    }

    override suspend fun deleteAllById(folderIds: List<Long>) {
        folderJpaRepository.deleteAllById(folderIds)
    }

    override suspend fun findAllByIds(folderIds: List<Long>): List<Folder> {
        return folderJpaRepository.findAllByIdIsIn(folderIds).map { it.toDomain() }
    }

    override suspend fun findFolderByFolderPath(folderPath: String): Folder? {
        return folderJpaRepository.findByFullPath(folderPath)?.toDomain()
    }

    override suspend fun existsByParentIdAndName(parentId: Long, name: String): Boolean {
        return folderJpaRepository.existsByParentIdAndName(parentId, name)
    }

    override suspend fun findAllByOwnerId(userId: Long): List<Folder> {
        return folderJpaRepository.findAllByOwnerId(userId).map { it.toDomain() }
    }
}