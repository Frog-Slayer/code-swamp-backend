package dev.codeswamp.core.article.domain.article.exception.folder

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

data class FolderMoveNotAllowedException(
    val path: String,
    val targetPath: String
): DomainBadRequestException("Cannot move folder $path to its descendant $targetPath")