package dev.codeswamp.article.domain.article.exception

import dev.codeswamp.article.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainBadRequestException

class DuplicatedSlugException(
    slug: String,
): DomainBadRequestException(
    DomainBadRequestErrorCode.DUPLICATED_SLUG,
    "Slug ($slug) already exists in the folder"
)