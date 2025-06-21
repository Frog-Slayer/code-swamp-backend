package dev.codeswamp.core.infrastructure.exception

sealed interface InfraErrorCode{
    val code: String
}

abstract class InfrastructureException(
    val errorCode: InfraErrorCode,
    message: String
) : RuntimeException(message)

