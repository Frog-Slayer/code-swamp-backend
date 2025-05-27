package dev.codeswamp.core.folder.application.impl

import dev.codeswamp.core.folder.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.folder.application.service.FolderApplicationService
import dev.codeswamp.core.folder.domain.service.FolderService
import dev.codeswamp.core.user.application.acl.FolderServiceAcl
import org.springframework.stereotype.Service

@Service
class FolderApplicationServiceImpl(
    private val folderService: FolderService
): FolderApplicationService {
    override fun create(createFolderCommand: CreateFolderCommand) {
        folderService.create(
            createFolderCommand.userId,
            createFolderCommand.name,
            createFolderCommand.parentId
        )
    }
}