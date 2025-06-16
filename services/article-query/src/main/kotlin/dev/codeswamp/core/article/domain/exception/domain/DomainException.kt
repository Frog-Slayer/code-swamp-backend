package dev.codeswamp.core.article.domain.exception.domain

interface DomainErrorCode {
    val code: String
}

abstract class DomainException(
    val errorCode: DomainErrorCode,
    message: String
): RuntimeException(message)

