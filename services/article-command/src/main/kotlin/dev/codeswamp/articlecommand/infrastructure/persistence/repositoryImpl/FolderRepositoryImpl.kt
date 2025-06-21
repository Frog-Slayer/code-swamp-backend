package dev.codeswamp.articlecommand.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlecommand.domain.folder.model.Folder
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity.FolderEntity
import dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.repository.FolderR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val folderR2dbcRepository: FolderR2dbcRepository,
) : FolderRepository {

    override suspend fun save(folder: Folder): Folder {
        val parent = folder.parentId?.let { folderR2dbcRepository.findById(folder.parentId) }
        val saved = folderR2dbcRepository.save(FolderEntity.Companion.from(folder, parent)).toDomain()
        return saved
    }

    //TODO: bulk로 하위 폴더 전부 삭제
    override suspend fun delete(folder: Folder) {
        folderR2dbcRepository.deleteById(folder.id)
    }

    override suspend fun findById(folderId: Long): Folder? {
        return folderR2dbcRepository.findById(folderId)?.toDomain()
    }

    override suspend fun updateDescendentsFullPath(oldFullPath: String, newFullPath: String) {
        folderR2dbcRepository.bulkUpdateFullPath(oldFullPath, newFullPath, "$oldFullPath/%")
    }

    override suspend fun findAllDescendantIdsByFolder(folder: Folder): List<Long> {
        return folderR2dbcRepository.findAllDescendantIdsByFolderFullPath("${folder.fullPath}/%")
    }

    override suspend fun deleteAllById(folderIds: List<Long>) {
        folderR2dbcRepository.deleteAllById(folderIds)
    }

    override suspend fun findAllByIds(folderIds: List<Long>): List<Folder> {
        return folderR2dbcRepository.findAllByIdIsIn(folderIds).map { it.toDomain() }
    }

    override suspend fun findFolderByFolderPath(folderPath: String): Folder? {
        return folderR2dbcRepository.findByFullPath(folderPath)?.toDomain()
    }

    override suspend fun existsByParentIdAndName(parentId: Long, name: String): Boolean {
        return folderR2dbcRepository.existsByParentIdAndName(parentId, name)
    }

    override suspend fun findAllByOwnerId(userId: Long): List<Folder> {
        return folderR2dbcRepository.findAllByOwnerId(userId).map { it.toDomain() }
    }
}