package dev.codeswamp.core.article.domain.article.exception

class InvalidSlugFormatException(
    slug: String
): DomainBadRequestException("Invalid slug format: $slug")