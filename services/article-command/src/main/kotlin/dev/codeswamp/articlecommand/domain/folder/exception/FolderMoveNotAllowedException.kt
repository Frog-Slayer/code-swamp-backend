package dev.codeswamp.articlecommand.domain.folder.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class FolderMoveNotAllowedException(
    path: String,
    targetPath: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.INVALID_FOLDER_MOVE,
    "Cannot move folder $path to its descendant $targetPath"
)