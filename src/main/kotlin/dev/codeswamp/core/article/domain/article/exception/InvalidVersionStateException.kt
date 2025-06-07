package dev.codeswamp.core.article.domain.article.exception

class InvalidVersionStateException(
    message: String,
    reason: String,
) : DomainConflictException("$message: $reason")