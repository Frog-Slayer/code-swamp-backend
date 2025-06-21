package dev.codeswamp.article.domain.folder.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class DuplicatedFolderNameException(
    name: String
) : DomainBadRequestException(
    DomainBadRequestErrorCode.DUPLICATED_FOLDER_NAME,
    "Folder name ($name) already exists in the folder"
)