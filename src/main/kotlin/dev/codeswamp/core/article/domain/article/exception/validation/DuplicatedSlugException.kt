package dev.codeswamp.core.article.domain.article.exception.validation

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

class DuplicatedSlugException(
    slug: String,
): DomainBadRequestException("Slug ($slug) already exists in the folder")