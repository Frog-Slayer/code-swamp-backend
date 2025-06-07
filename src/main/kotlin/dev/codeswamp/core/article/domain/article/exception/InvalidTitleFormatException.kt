package dev.codeswamp.core.article.domain.article.exception

class InvalidTitleFormatException(
    title: String
): DomainBadRequestException("Invalid title format: $title")