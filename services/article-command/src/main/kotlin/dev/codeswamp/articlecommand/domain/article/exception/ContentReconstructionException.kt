package dev.codeswamp.articlecommand.domain.article.exception

import dev.codeswamp.articlecommand.domain.exception.domain.DomainConflictErrorCode
import dev.codeswamp.articlecommand.domain.exception.domain.DomainConflictException

class ContentReconstructionException(
    reason: String
) : DomainConflictException(
    DomainConflictErrorCode.CONTENT_RECONSTRUCTION_FAILURE,
    "Content reconstruct content: $reason"
)