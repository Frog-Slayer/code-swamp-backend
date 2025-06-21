package dev.codeswamp.core.infrastructure.exception

sealed interface InfraNotFoundErrorCode : InfraErrorCode

object InfraNotFound: InfraNotFoundErrorCode {
    override val code = "INFRA_NOT_FOUND"
}

abstract class InfraNotFoundException(
    errorCode: InfraNotFoundErrorCode,
    message: String
) : InfrastructureException(errorCode, message)