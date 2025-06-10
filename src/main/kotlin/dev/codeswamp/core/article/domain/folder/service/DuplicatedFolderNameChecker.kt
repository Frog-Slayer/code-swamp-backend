package dev.codeswamp.core.article.domain.folder.service

import dev.codeswamp.core.article.domain.folder.exception.DuplicatedFolderNameException
import dev.codeswamp.core.article.domain.folder.model.Name
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class DuplicatedFolderNameChecker (
    private val folderRepository: FolderRepository
){
    fun checkDuplicatedFolderNameInSameLevel(folderId: Long, name: Name) {
        if (folderRepository.existsByParentIdAndName(folderId, name.value)) throw DuplicatedFolderNameException(name.value)
    }
}