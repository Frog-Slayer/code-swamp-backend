package dev.codeswamp.article.domain.folder.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class RootFolderDeletionException(
    folderName: String
): DomainBadRequestException(
    DomainBadRequestErrorCode.REQUEST_TO_DELETE_ROOT,
    "Cannot delete root folder $folderName"
)