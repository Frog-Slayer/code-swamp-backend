package dev.codeswamp.core.domain.exception

sealed interface DomainBadRequestErrorCode :  DomainErrorCode

object DomainBadRequest : DomainBadRequestErrorCode {
    override val code = "DOMAIN_BAD_REQUEST"
}

abstract class DomainBadRequestException(
    errorCode: DomainBadRequestErrorCode,
    message: String
) : DomainException(errorCode, message)