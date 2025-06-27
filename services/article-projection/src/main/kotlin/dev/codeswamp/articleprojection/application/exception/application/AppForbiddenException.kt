package dev.codeswamp.articleprojection.application.exception.application

import dev.codeswamp.core.common.exception.ForbiddenErrorCode
import dev.codeswamp.core.common.exception.ForbiddenException

enum class AppForbiddenErrorCode(
    override val code: String,
) : ForbiddenErrorCode{
    APP_FORBIDDEN("APP_FORBIDDEN"),
    PRIVATE_ARTICLE("PRIVATE_ARTICLE"),
}

open class AppForbiddenException(
    errorCode: AppForbiddenErrorCode,
    message: String
) : ForbiddenException(errorCode, message)