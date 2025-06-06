package dev.codeswamp.core.article.infrastructure.acl

import dev.codeswamp.core.article.application.dto.command.CreateRootFolderCommand
import dev.codeswamp.core.article.application.usecase.FolderUseCase
import dev.codeswamp.core.user.application.acl.FolderAcl
import org.springframework.stereotype.Component

@Component
class UserFolderAclImpl (
    private val folderUseCase: FolderUseCase
) : FolderAcl {
    override fun createRootFolder(userId: Long, username: String) {
        folderUseCase.createRoot(CreateRootFolderCommand(userId, username))
    }
}