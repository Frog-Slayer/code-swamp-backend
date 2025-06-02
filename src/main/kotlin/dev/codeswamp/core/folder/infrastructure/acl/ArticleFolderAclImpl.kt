package dev.codeswamp.core.folder.infrastructure.acl

import dev.codeswamp.core.article.application.service.acl.FolderAcl
import dev.codeswamp.core.folder.application.service.FolderApplicationService
import org.springframework.stereotype.Component

@Component
class ArticleFolderAclImpl(
    private val folderApplicationService: FolderApplicationService
) : FolderAcl {
    override fun getFolderIdByPath(path: String): Long? {
        return folderApplicationService.findFolderByFullPath(path)?.id
    }
}