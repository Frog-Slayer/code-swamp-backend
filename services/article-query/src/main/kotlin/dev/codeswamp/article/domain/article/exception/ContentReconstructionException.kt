package dev.codeswamp.article.domain.article.exception

import dev.codeswamp.article.domain.exception.domain.DomainConflictErrorCode
import dev.codeswamp.article.domain.exception.domain.DomainConflictException

class ContentReconstructionException(
    reason: String
) : DomainConflictException(
    DomainConflictErrorCode.CONTENT_RECONSTRUCTION_FAILURE,
    "Content reconstruct content: $reason"
)