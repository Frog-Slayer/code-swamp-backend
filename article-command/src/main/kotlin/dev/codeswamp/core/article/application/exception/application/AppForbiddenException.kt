package dev.codeswamp.core.article.application.exception.application

enum class AppForbiddenErrorCode (
    override val code: String,
) : AppErrorCode {
    APP_FORBIDDEN("APP_FORBIDDEN"),
}

open class AppForbiddenException(
    errorCode: AppForbiddenErrorCode,
    message: String
): AppException( errorCode, message )