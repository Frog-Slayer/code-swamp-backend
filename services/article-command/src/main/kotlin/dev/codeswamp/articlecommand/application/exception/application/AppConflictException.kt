package dev.codeswamp.articlecommand.application.exception.application

import dev.codeswamp.core.common.exception.ConflictErrorCode
import dev.codeswamp.core.common.exception.ConflictException

enum class AppConflictErrorCode(
    override val code: String,
) : ConflictErrorCode{
    APP_CONFLICT("APP_CONFLICT"),
}

abstract class AppConflictException(
    errorCode: AppConflictErrorCode,
    message: String
) : ConflictException(errorCode, message)