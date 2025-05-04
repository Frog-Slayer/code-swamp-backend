package dev.codeswamp.domain.folder.domain.service

import dev.codeswamp.domain.folder.domain.entity.Folder
import dev.codeswamp.domain.folder.domain.repository.FolderRepository
import dev.codeswamp.domain.folder.infrastructure.persistence.entity.FolderEntity
import org.springframework.stereotype.Service

@Service
class FolderService (
    private val folderRepository: FolderRepository
){

    fun create(ownerId: Long, name: String, parentId: Long) : Folder {
        val folder = Folder(
            ownerId = ownerId,
            name = name,
            parentId = parentId
        )
        return folderRepository.save(folder)
    }

    //TODO - Exception 구체화
    fun findById(id: Long): Folder {
        return folderRepository.findById(id)
            .orElseThrow{ NoSuchElementException("no")}
    }

    fun findAllByIds(folderIds: List<Long>) : List<Folder> {
        return folderRepository.findAllByIds(folderIds)
    }

    //TODO - Exception 구체화
    fun rename(folderId: Long, newName: String) {
        val folder = folderRepository.findById(folderId)
            .orElseThrow{ IllegalArgumentException("Folder with id $folderId not found") }

        folder.rename(newName)
        folderRepository.save(folder)
    }

    //TODO - Exception 구체화
    fun moveTo(folderId: Long, newParentId: Long) {
       val folder = folderRepository.findById(folderId)
            .orElseThrow{ IllegalArgumentException("Folder with id $folderId not found") }

        folder.moveTo(newParentId)
        folderRepository.save(folder)
    }

    fun delete(folderId: Long) {
        val folder = folderRepository.findById(folderId)
            .orElseThrow{ IllegalArgumentException("Folder with id $folderId not found") }

        folderRepository.delete(folder)
    }



}