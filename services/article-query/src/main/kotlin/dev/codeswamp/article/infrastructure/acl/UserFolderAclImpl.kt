package dev.codeswamp.article.infrastructure.acl

import dev.codeswamp.article.application.usecase.command.folder.FolderCommandUseCaseFacade
import dev.codeswamp.article.application.usecase.command.folder.create.CreateRootFolderCommand
import dev.codeswamp.core.user.application.acl.FolderAcl
import org.springframework.stereotype.Component

@Component
class UserFolderAclImpl (
    private val folderService : FolderCommandUseCaseFacade
) : FolderAcl {
    override fun createRootFolder(userId: Long, username: String) {
        folderService.createRoot(CreateRootFolderCommand(userId, username))
    }
}