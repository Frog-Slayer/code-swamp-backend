package dev.codeswamp.core.infrastructure.exception

sealed interface InfraBadRequestErrorCode: InfraErrorCode

object InfraBadRequest:  InfraBadRequestErrorCode {
    override val code = "INFRA_BAD_REQUEST"
}

abstract class AppBadRequestException(
    errorCode: InfraBadRequestErrorCode,
    message: String
) : InfrastructureException(errorCode, message)