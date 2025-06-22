package dev.codeswamp.core.application.exception

sealed interface ApplicationErrorCode {
    val code: String
}

abstract class AppException(
    val errorCode: ApplicationErrorCode,
    message: String
) : RuntimeException(message)

