package dev.codeswamp.core.article.domain.article.exception.validation

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

data class DuplicatedFolderNameException (
    val name: String
): DomainBadRequestException("Folder name ($name) already exists in the folder")