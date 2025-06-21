package dev.codeswamp.infrastructure.exception

sealed interface InfraForbiddenErrorCode: InfraErrorCode

object InfraForbidden: InfraForbiddenErrorCode{
    override val code = "INFRA_FORBIDDEN"
}

abstract class InfraForbiddenException(
    errorCode: InfraForbiddenErrorCode,
    message: String
) : InfrastructureException(errorCode, message)