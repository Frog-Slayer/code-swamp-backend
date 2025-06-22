package dev.codeswamp.article.application.exception.application

enum class AppNotFoundErrorCode(
    override val code: String,
) : AppErrorCode {
    APP_NOT_FOUND("APP_NOT_FOUND"),
    ARTICLE_NOT_FOUND("ARTICLE_NOT_FOUND"),
    FOLDER_NOT_FOUND("FOLDER_NOT_FOUND"),
}

open class AppNotFoundException(
    errorCode: AppNotFoundErrorCode,
    message: String
) : AppException(errorCode, message)