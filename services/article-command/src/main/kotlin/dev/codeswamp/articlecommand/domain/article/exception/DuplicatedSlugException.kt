package dev.codeswamp.articlecommand.domain.article.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainBadRequestException

class DuplicatedSlugException(
    slug: String,
) : DomainBadRequestException(
    DomainBadRequestErrorCode.DUPLICATED_SLUG,
    "Slug ($slug) already exists in the folder"
)