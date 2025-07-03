package dev.codeswamp.articlequery.infrastructure.persistence.repositoryImpl

import dev.codeswamp.articlequery.application.readmodel.model.Folder
import dev.codeswamp.articlequery.application.readmodel.repository.FolderRepository
import dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.repository.FolderR2dbcRepository
import org.springframework.stereotype.Repository

@Repository
class FolderRepositoryImpl(
    private val folderR2dbcRepository: FolderR2dbcRepository,
) : FolderRepository {

    override suspend fun findById(folderId: Long): Folder? {
        return folderR2dbcRepository.findById(folderId)?.toDomain()
    }

    override suspend fun findAllByIds(folderIds: List<Long>): List<Folder> {
        return folderR2dbcRepository.findAllByIdIsIn(folderIds).map { it.toDomain() }
    }

    override suspend fun findFolderByFolderPath(folderPath: String): Folder? {
        return folderR2dbcRepository.findByFullPath(folderPath)?.toDomain()
    }

    override suspend fun findAllByOwnerId(userId: Long): List<Folder> {
        return folderR2dbcRepository.findAllByOwnerId(userId).map { it.toDomain() }
    }
}