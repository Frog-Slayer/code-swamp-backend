package dev.codeswamp.domain.exception

sealed interface DomainConflictErrorCode :  DomainErrorCode

object DomainConflict :  DomainConflictErrorCode {
    override val code = "DOMAIN_CONFLICT"
}

abstract class DomainConflictException(
    errorCode: DomainConflictErrorCode,
    message: String
) : DomainException(errorCode, message)