package dev.codeswamp.articlecommand.application.exception.application

import dev.codeswamp.core.common.exception.BadRequestErrorCode
import dev.codeswamp.core.common.exception.BadRequestException

enum class AppBadRequestErrorCode(
    override val code: String,
) : BadRequestErrorCode {
    APP_BAD_REQUEST("APP_BAD_REQUEST"),
    APP_ARTICLE_VERSION_MISMATCH("APP_ARTICLE_VERSION_MISMATCH"),
}

abstract class AppBadRequestException(
    errorCode: AppBadRequestErrorCode,
    message: String
) : BadRequestException(errorCode, message)