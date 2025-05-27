package dev.codeswamp.core.folder.infrastructure.persistence.repository

import dev.codeswamp.core.folder.domain.entity.Folder
import dev.codeswamp.core.folder.domain.repository.FolderRepository
import dev.codeswamp.core.folder.infrastructure.persistence.entity.FolderEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class FolderRepositoryImpl(
    private val folderJpaRepository: FolderJpaRepository
) : FolderRepository {

    override fun save(folder: Folder): Folder {
        val parent = folder.parentId?.let{folderJpaRepository.findByIdOrNull(folder.parentId)}
        return folderJpaRepository.save(FolderEntity.from(folder, parent)).toDomain()
    }

    override fun delete(folder: Folder) {
        TODO("Not yet implemented")
    }

    override fun findById(folderId: Long): Optional<Folder> {
        TODO("Not yet implemented")
    }

    override fun findAllByIds(folderIds: List<Long>): List<Folder> {
        TODO("Not yet implemented")
    }

}