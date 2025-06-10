package dev.codeswamp.core.article.domain.article.exception.folder

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

class FolderMoveNotAllowedException(
    path: String,
    targetPath: String
): DomainBadRequestException("Cannot move folder $path to its descendant $targetPath")