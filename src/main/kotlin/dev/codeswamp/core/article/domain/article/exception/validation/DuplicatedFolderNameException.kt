package dev.codeswamp.core.article.domain.article.exception.validation

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

class DuplicatedFolderNameException (
    name: String
): DomainBadRequestException("Folder name ($name) already exists in the folder")