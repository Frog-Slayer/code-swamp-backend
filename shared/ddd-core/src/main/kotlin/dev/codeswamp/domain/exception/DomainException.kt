package dev.codeswamp.domain.exception

sealed interface DomainErrorCode {
    val code: String
}

abstract class DomainException(
    val errorCode: DomainErrorCode,
    message: String
) : RuntimeException(message)

