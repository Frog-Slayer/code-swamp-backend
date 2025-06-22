package dev.codeswamp.article.application.exception.application

enum class AppBadRequestErrorCode(
    override val code: String,
) : AppErrorCode {
    APP_BAD_REQUEST("APP_BAD_REQUEST"),
}

abstract class AppBadRequestException(
    errorCode: AppBadRequestErrorCode,
    message: String
) : AppException(errorCode, message)