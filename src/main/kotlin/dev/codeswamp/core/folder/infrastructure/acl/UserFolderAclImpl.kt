package dev.codeswamp.core.folder.infrastructure.acl

import dev.codeswamp.core.folder.application.dto.command.CreateFolderCommand
import dev.codeswamp.core.folder.application.service.FolderApplicationService
import dev.codeswamp.core.user.application.acl.FolderServiceAcl
import org.springframework.stereotype.Component

@Component
class UserFolderAclImpl (
    private val folderApplicationService: FolderApplicationService
) : FolderServiceAcl {
    override fun createRootFolder(userId: Long) {
        folderApplicationService.create(CreateFolderCommand(userId))
    }
}