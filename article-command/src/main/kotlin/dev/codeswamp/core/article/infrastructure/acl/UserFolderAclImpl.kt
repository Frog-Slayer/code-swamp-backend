package dev.codeswamp.core.article.infrastructure.acl

import dev.codeswamp.core.article.application.usecase.command.folder.FolderUseCaseFacade
import dev.codeswamp.core.article.application.usecase.command.folder.create.CreateRootFolderCommand
import dev.codeswamp.core.user.application.acl.FolderAcl
import org.springframework.stereotype.Component

@Component
class UserFolderAclImpl (
    private val folderService : FolderUseCaseFacade
) : FolderAcl {
    override fun createRootFolder(userId: Long, username: String) {
        folderService.createRoot(CreateRootFolderCommand(userId, username))
    }
}