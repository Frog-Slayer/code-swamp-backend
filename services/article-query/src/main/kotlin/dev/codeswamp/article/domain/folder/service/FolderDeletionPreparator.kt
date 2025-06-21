package dev.codeswamp.article.domain.folder.service

import dev.codeswamp.article.domain.folder.exception.RootFolderDeletionException
import dev.codeswamp.article.domain.folder.model.Folder
import dev.codeswamp.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service
import java.time.Instant

data class FolderDeletionPreparationResult(
    val folder: Folder,
    val descendantIds: List<Long>,
)

@Service
class FolderDeletionPreparator(
    private val folderRepository: FolderRepository,
) {
    fun prepareDeletion(folder: Folder, deletedAt: Instant): FolderDeletionPreparationResult {
        if (folder.isRoot()) throw RootFolderDeletionException(folder.name.value)

        val descendants = folderRepository.findAllDescendantIdsByFolder(folder)

        return FolderDeletionPreparationResult(
            folder.markAsDeleted(descendants, deletedAt),
            descendants
        )
    }
}