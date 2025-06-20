package dev.codeswamp.article.domain.folder.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class FolderMoveNotAllowedException(
    path: String,
    targetPath: String
): DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_FOLDER_MOVE,
    "Cannot move folder $path to its descendant $targetPath"
)