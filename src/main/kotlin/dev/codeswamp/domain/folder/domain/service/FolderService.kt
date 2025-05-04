package dev.codeswamp.domain.folder.domain.service

import dev.codeswamp.domain.folder.domain.entity.Folder
import org.springframework.stereotype.Service

@Service
class FolderService {

    fun create(ownerId: Long, name: String, parentId: Long?) : Folder {
        TODO("Not yet implemented")
    }

    fun rename(folderId: Long, newName: String) {
        TODO("Not yet implemented")
    }

    fun moveTo(folderId: Long, newParentId: Long) {
        TODO("Not yet implemented")
    }

    fun delete(folderId: Long) {
        TODO("Not yet implemented")
    }





}