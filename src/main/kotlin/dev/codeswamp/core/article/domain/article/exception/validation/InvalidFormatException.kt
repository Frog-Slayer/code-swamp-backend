package dev.codeswamp.core.article.domain.article.exception.validation

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

class InvalidFormatException(
    message: String
): DomainBadRequestException("Invalid format: $message")