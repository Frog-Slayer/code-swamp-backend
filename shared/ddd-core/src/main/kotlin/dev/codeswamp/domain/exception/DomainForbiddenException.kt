package dev.codeswamp.domain.exception

sealed interface DomainForbiddenErrorCode : DomainErrorCode

object DomainForbidden: DomainForbiddenErrorCode {
    override val code = "DOMAIN_FORBIDDEN"
}

abstract class DomainForbiddenException(
    errorCode: DomainForbiddenErrorCode,
    message: String
) : DomainException(errorCode, message)