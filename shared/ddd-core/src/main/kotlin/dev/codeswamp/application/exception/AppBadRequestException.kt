package dev.codeswamp.application.exception

sealed interface AppBadRequestErrorCode: ApplicationErrorCode

object AppBadRequest:  AppBadRequestErrorCode {
    override val code = "APP_BAD_REQUEST"
}

abstract class AppBadRequestException(
    errorCode: AppBadRequestErrorCode,
    message: String
) : AppException(errorCode, message)