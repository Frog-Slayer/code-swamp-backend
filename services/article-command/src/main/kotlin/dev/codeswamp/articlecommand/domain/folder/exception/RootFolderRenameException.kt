package dev.codeswamp.articlecommand.domain.folder.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class RootFolderRenameException(
    folderName: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.REQUEST_TO_RENAME_ROOT,
    "Cannot rename root folder $folderName"
)