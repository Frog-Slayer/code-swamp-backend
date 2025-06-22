package dev.codeswamp.articlecommand.domain.folder.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainForbiddenErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainForbiddenException

class ForbiddenFolderAccessException(
    folderId: Long
) : DomainForbiddenException(
    DomainForbiddenErrorCode.FORBIDDEN_FOLDER_ACCESS,
    "You cannot access this folder with ID $folderId"
)