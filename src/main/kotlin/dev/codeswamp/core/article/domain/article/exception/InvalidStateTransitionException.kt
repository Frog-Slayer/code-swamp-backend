package dev.codeswamp.core.article.domain.article.exception

class InvalidStateTransitionException : DomainConflictException(
    "Cannot archive published version"
)