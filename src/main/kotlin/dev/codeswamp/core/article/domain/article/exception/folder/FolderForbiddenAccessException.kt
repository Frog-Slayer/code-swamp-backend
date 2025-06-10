package dev.codeswamp.core.article.domain.article.exception.folder

import dev.codeswamp.core.article.domain.article.exception.domain.DomainForbiddenException

class FolderForbiddenAccessException (
    folderId: Long
) : DomainForbiddenException("You cannot access this folder with ID $folderId")