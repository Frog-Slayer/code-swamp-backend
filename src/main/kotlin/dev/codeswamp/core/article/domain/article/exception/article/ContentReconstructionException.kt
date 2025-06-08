package dev.codeswamp.core.article.domain.article.exception.article

import dev.codeswamp.core.article.domain.article.exception.domain.DomainConflictException

class ContentReconstructionException(
    reason: String
) : DomainConflictException("Content reconstruct content: $reason")