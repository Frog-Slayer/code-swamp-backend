package dev.codeswamp.core.article.domain.folder.service

import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class DuplicatedFolderNameChecker (
    private val folderRepository: FolderRepository
){
    fun checkDuplicatedFolderNameInSameLevel(folderId: Long, name: String) {
        if (folderRepository.existsByParentIdAndName(folderId, name)) throw Exception("duplicated folder name")
    }
}