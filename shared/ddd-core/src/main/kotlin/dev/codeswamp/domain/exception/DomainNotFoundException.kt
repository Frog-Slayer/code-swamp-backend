package dev.codeswamp.domain.exception

sealed interface DomainNotFoundErrorCode : DomainErrorCode

object DomainNotFound : DomainNotFoundErrorCode {
    override val code = "DOMAIN_NOT_FOUND"
}

abstract class DomainNotFoundException(
    errorCode: DomainNotFoundErrorCode,
    message: String
) : DomainException(errorCode, message)