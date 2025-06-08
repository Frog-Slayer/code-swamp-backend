package dev.codeswamp.core.article.domain.article.exception.article

import dev.codeswamp.core.article.domain.article.exception.domain.DomainBadRequestException

class InvalidArticleStateException(
    message: String,
    reason: String
) : DomainBadRequestException("$message: $reason")