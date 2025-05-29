package dev.codeswamp.core.folder.application.impl

import dev.codeswamp.core.folder.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.folder.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.folder.application.dto.command.DeleteFolderCommand
import dev.codeswamp.core.folder.application.dto.command.MoveFolderCommand
import dev.codeswamp.core.folder.application.service.FolderApplicationService
import dev.codeswamp.core.folder.domain.entity.Folder
import dev.codeswamp.core.folder.domain.repository.FolderRepository
import dev.codeswamp.core.folder.domain.service.FolderService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class FolderApplicationServiceImpl(
    private val folderService: FolderService,
): FolderApplicationService {
    override fun findFolderByFullPath(fullPath: String): Folder? {
        val splitPath = fullPath.split('/')
        val rootName =  splitPath.first()
        val paths = splitPath.drop(1)

        return folderService.findFolderByFullPath(rootName, paths)
    }

    override fun create(createFolderCommand: CreateFolderCommand) {
        val parent = requireNotNull(folderService.findById(createFolderCommand.parentId)) {"parent folder not found"}

        parent.checkOwnership(createFolderCommand.userId)

        if (folderService.checkDuplicatedFolderNameInSameLevel(parent, createFolderCommand.name)) throw IllegalArgumentException("folder with name ${createFolderCommand.name}already exists in the same level")

        folderService.create(Folder(
            ownerId = createFolderCommand.userId,
            name = createFolderCommand.name,
            parentId = createFolderCommand.parentId
        ))
    }

    override fun createRoot(createRootFolderCommand: CreateRootFolderCommand) {
        folderService.create(Folder(
            ownerId = createRootFolderCommand.userId,
            name = "@${createRootFolderCommand.name}",
        ))
    }

    override fun move(moveFolderCommand: MoveFolderCommand) {
        val userId = moveFolderCommand.userId

        val folder = requireNotNull(folderService.findById(moveFolderCommand.folderId)) { "folder not found" }
        folder.checkOwnership(userId)

        val parent = requireNotNull(folderService.findById(moveFolderCommand.parentId)) {"parent folder not found"}
        parent.checkOwnership(userId)

        folderService.moveTo(folder, parent)
    }

    override fun delete(deleteFolderCommand: DeleteFolderCommand) {
        val userId = deleteFolderCommand.userId
        val folder = requireNotNull(folderService.findById(deleteFolderCommand.folderId)) { "folder not found" }

        folder.checkOwnership(userId)

        folderService.delete(folder)
    }
}