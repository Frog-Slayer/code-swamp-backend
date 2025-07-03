package dev.codeswamp.articlequery.application.exception.application

import dev.codeswamp.core.common.exception.NotFoundErrorCode
import dev.codeswamp.core.common.exception.NotFoundException

enum class AppNotFoundErrorCode(
    override val code: String,
) : NotFoundErrorCode {
    APP_NOT_FOUND("APP_NOT_FOUND"),
    ARTICLE_NOT_FOUND("ARTICLE_NOT_FOUND"),
    FOLDER_NOT_FOUND("FOLDER_NOT_FOUND"),
}

open class AppNotFoundException(
    errorCode: AppNotFoundErrorCode,
    message: String
) : NotFoundException(errorCode, message)