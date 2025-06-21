package dev.codeswamp.infrastructure.exception

sealed interface InfraConflictErrorCode: InfraErrorCode

object InfraConflict : InfraConflictErrorCode{
    override val code = "INFRA_CONFLICT"
}

abstract class AppConflictException(
    errorCode: InfraConflictErrorCode,
    message: String
) : InfrastructureException(errorCode, message)