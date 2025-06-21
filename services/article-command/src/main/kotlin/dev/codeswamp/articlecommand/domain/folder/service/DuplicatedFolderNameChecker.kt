package dev.codeswamp.articlecommand.domain.folder.service

import dev.codeswamp.articlecommand.domain.folder.exception.DuplicatedFolderNameException
import dev.codeswamp.articlecommand.domain.folder.model.Name
import dev.codeswamp.articlecommand.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class DuplicatedFolderNameChecker(
    private val folderRepository: FolderRepository
) {
    suspend fun checkDuplicatedFolderNameInSameLevel(folderId: Long, name: Name) {
        if (folderRepository.existsByParentIdAndName(folderId, name.value)) throw DuplicatedFolderNameException(name.value)
    }
}