package dev.codeswamp.article.application.exception.application

enum class AppConflictErrorCode(
    override val code: String,
) : AppErrorCode {
    APP_CONFLICT("APP_CONFLICT"),
}

abstract class AppConflictException(
    errorCode: AppConflictErrorCode,
    message: String
) : AppException(errorCode, message)