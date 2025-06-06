package dev.codeswamp.core.article.application.usecase.impl

import dev.codeswamp.core.article.application.usecase.FolderUseCase
import dev.codeswamp.core.article.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.article.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.article.application.dto.command.DeleteFolderCommand
import dev.codeswamp.core.article.application.dto.command.MoveFolderCommand
import dev.codeswamp.core.article.domain.folder.model.Folder
import dev.codeswamp.core.article.domain.folder.service.FolderDomainService
import org.springframework.stereotype.Service

@Service
class FolderUseCaseImpl(
    private val folderDomainService: FolderDomainService,
): FolderUseCase {
    override fun create(createFolderCommand: CreateFolderCommand) {
        val parent = requireNotNull(folderDomainService.findById(createFolderCommand.parentId)) {"parent folder not found"}

        parent.checkOwnership(createFolderCommand.userId)

        if (folderDomainService.checkDuplicatedFolderNameInSameLevel(parent, createFolderCommand.name)) throw IllegalArgumentException("folder with name ${createFolderCommand.name}already exists in the same level")

        folderDomainService.create(
            Folder(
                ownerId = createFolderCommand.userId,
                name = createFolderCommand.name,
                parentId = createFolderCommand.parentId
            )
        )
    }

    override fun createRoot(createRootFolderCommand: CreateRootFolderCommand) {
        folderDomainService.create(
            Folder(
                ownerId = createRootFolderCommand.userId,
                name = "@${createRootFolderCommand.name}",
            )
        )
    }

    override fun move(moveFolderCommand: MoveFolderCommand) {
        val userId = moveFolderCommand.userId

        val folder = requireNotNull(folderDomainService.findById(moveFolderCommand.folderId)) { "folder not found" }
        folder.checkOwnership(userId)

        val parent = requireNotNull(folderDomainService.findById(moveFolderCommand.parentId)) {"parent folder not found"}
        parent.checkOwnership(userId)

        folderDomainService.moveTo(folder, parent)
    }

    override fun delete(deleteFolderCommand: DeleteFolderCommand) {
        val userId = deleteFolderCommand.userId
        val folder = requireNotNull(folderDomainService.findById(deleteFolderCommand.folderId)) { "folder not found" }

        folder.checkOwnership(userId)

        folderDomainService.delete(folder)
    }
}