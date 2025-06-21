package dev.codeswamp.core.application.exception

sealed interface AppForbiddenErrorCode: ApplicationErrorCode

object AppForbidden: AppForbiddenErrorCode{
    override val code = "APP_FORBIDDEN"
}

abstract class AppForbiddenException(
    errorCode: AppForbiddenErrorCode,
    message: String
) : AppException(errorCode, message)