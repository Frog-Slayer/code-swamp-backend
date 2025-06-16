package dev.codeswamp.core.article.application.exception.application

interface AppErrorCode {
    val code : String
}

abstract class AppException(
    val errorCode: AppErrorCode,
    message: String
): RuntimeException(message)

