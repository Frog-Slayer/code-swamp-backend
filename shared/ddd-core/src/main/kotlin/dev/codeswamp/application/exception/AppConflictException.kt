package dev.codeswamp.application.exception


sealed interface AppConflictErrorCode: ApplicationErrorCode

object AppConflict : AppConflictErrorCode{
    override val code = "APP_CONFLICT"
}

abstract class AppConflictException(
    errorCode: AppConflictErrorCode,
    message: String
) : AppException(errorCode, message)