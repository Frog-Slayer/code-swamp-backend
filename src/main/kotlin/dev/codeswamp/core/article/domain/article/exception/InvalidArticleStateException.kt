package dev.codeswamp.core.article.domain.article.exception

class InvalidArticleStateException(
    message: String,
    reason: String
) : DomainBadRequestException("$message: $reason")