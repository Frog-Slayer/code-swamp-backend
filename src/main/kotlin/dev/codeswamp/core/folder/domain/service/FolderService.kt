package dev.codeswamp.core.folder.domain.service

import dev.codeswamp.core.folder.domain.entity.Folder
import dev.codeswamp.core.folder.domain.repository.FolderRepository
import org.springframework.stereotype.Service

@Service
class FolderService (
    private val folderRepository: FolderRepository
){
    fun create(folder: Folder) : Folder {
        return folderRepository.save(folder)
    }

    //TODO - Exception 구체화
    fun findById(id: Long): Folder? {
        return folderRepository.findById(id)
    }

    fun findAllByIds(folderIds: List<Long>) : List<Folder> {
        return folderRepository.findAllByIds(folderIds)
    }

    //TODO - Exception 구체화
    fun moveTo(folder: Folder, newParent: Folder) {
        newParent.id?.let{ folder.parentId = it}
        folderRepository.save(folder)
    }

    fun delete(folder: Folder) {
        folderRepository.delete(folder)
    }

    fun findFolderByFullPath(rootName: String, paths: List<String>): Folder? {
        return folderRepository.findFolderByFullPath(rootName, paths)
    }

    fun checkDuplicatedFolderNameInSameLevel(parent: Folder, name: String) : Boolean{
        requireNotNull(parent.id) {"parent folder id required"}
        return folderRepository.existsByParentIdAndName(parent.id, name)
    }
}