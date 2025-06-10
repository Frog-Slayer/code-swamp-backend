package dev.codeswamp.core.article.domain.article.exception.folder

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

data class RootFolderRenameException(
    val folderName: String
): DomainBadRequestException("Cannot rename root folder $folderName")