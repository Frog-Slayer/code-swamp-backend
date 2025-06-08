package dev.codeswamp.core.article.domain.folder.service

import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class FolderPathResolver(
    private val folderRepository: FolderRepository,
){
    fun findFolderByFullPath(path: List<String>): Folder? {
        return folderRepository.findFolderByFullPath(path)
    }
}