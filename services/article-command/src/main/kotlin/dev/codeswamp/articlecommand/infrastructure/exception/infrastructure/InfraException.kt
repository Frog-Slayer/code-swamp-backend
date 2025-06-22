package dev.codeswamp.articlecommand.infrastructure.exception.infrastructure

interface InfraErrorCode {
    val code: String
}

abstract class InfraException(
    val errorCode: InfraErrorCode,
    message: String
) : RuntimeException(message)

