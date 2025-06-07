package dev.codeswamp.core.article.domain.article.exception

class DuplicatedSlugException(
    slug: String,
): DomainBadRequestException("Slug ($slug) already exists in the folder")