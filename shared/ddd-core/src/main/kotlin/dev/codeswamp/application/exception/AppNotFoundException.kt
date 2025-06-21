package dev.codeswamp.application.exception

sealed interface ApplicationNotFoundErrorCode : ApplicationErrorCode

object ApplicationNotFound: ApplicationNotFoundErrorCode {
    override val code = "APP_NOT_FOUND"
}

abstract class AppNotFoundException(
    errorCode: ApplicationNotFoundErrorCode,
    message: String
) : AppException(errorCode, message)