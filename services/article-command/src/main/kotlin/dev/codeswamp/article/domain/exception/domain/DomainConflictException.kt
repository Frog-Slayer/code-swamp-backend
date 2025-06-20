package dev.codeswamp.article.domain.exception.domain

enum class DomainConflictErrorCode(
    override val code: String
) : DomainErrorCode {
    DOMAIN_CONFLICT("DOMAIN_CONFLICT"),
    CONTENT_RECONSTRUCTION_FAILURE("CONTENT_RECONSTRUCTION_FAILURE")
}

abstract class DomainConflictException(
    errorCode: DomainConflictErrorCode,
    message: String
): DomainException( errorCode, message )