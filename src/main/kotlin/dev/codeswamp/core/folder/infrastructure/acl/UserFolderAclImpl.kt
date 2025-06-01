package dev.codeswamp.core.folder.infrastructure.acl

import dev.codeswamp.core.folder.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.folder.application.service.FolderApplicationService
import dev.codeswamp.core.user.application.acl.FolderAcl
import org.springframework.stereotype.Component

@Component
class UserFolderAclImpl (
    private val folderApplicationService: FolderApplicationService
) : FolderAcl {
    override fun createRootFolder(userId: Long, username: String) {
        folderApplicationService.createRoot(CreateRootFolderCommand(userId, username))
    }
}