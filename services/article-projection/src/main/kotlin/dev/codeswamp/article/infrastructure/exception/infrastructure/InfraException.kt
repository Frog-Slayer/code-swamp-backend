package dev.codeswamp.article.infrastructure.exception.infrastructure

interface InfraErrorCode {
    val code: String
}

abstract class InfraException(
    val errorCode: InfraErrorCode,
    message: String
) : RuntimeException(message)

