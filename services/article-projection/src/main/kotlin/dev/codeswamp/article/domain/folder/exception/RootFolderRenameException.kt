package dev.codeswamp.article.domain.folder.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class RootFolderRenameException(
    folderName: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.REQUEST_TO_RENAME_ROOT,
    "Cannot rename root folder $folderName"
)