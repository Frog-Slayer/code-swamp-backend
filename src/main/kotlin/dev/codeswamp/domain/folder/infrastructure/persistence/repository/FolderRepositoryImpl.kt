package dev.codeswamp.domain.folder.infrastructure.persistence.repository

import dev.codeswamp.domain.folder.domain.entity.Folder
import dev.codeswamp.domain.folder.domain.repository.FolderRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class FolderRepositoryImpl(
    private val folderJpaRepository: FolderJpaRepository
) : FolderRepository {

    override fun save(folder: Folder): Folder {
        TODO("Not yet implemented")
    }

    override fun delete(folder: Folder) {
        TODO("Not yet implemented")
    }

    override fun findById(folderId: Long): Optional<Folder> {
        TODO("Not yet implemented")
    }

}