package dev.codeswamp.core.article.domain.folder.service

import dev.codeswamp.core.article.domain.folder.exception.RootFolderDeletionException
import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class FolderDeletionService(
    private val folderRepository: FolderRepository,
) {
    fun markFolderAndDescendantsAsDeleted(folder: Folder) : Folder {
        if (folder.isRoot()) throw RootFolderDeletionException(folder.name.value) //

        val descendants = folderRepository.findAllDescendantIdsByFolder(folder)

        return folder.markAsDeleted(descendants)
    }
}