package dev.codeswamp.articlecommand.domain.folder.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class RootFolderDeletionException(
    folderName: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.REQUEST_TO_DELETE_ROOT,
    "Cannot delete root folder $folderName"
)