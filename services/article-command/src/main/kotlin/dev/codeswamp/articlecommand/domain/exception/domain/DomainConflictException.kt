package dev.codeswamp.articlecommand.domain.exception.domain

import dev.codeswamp.core.common.exception.ConflictErrorCode
import dev.codeswamp.core.common.exception.ConflictException

enum class DomainConflictErrorCode(
    override val code: String
) : ConflictErrorCode {
    DOMAIN_CONFLICT("DOMAIN_CONFLICT"),
    CONTENT_RECONSTRUCTION_FAILURE("CONTENT_RECONSTRUCTION_FAILURE")
}

abstract class DomainConflictException(
    errorCode: DomainConflictErrorCode,
    message: String
) : ConflictException(errorCode, message)