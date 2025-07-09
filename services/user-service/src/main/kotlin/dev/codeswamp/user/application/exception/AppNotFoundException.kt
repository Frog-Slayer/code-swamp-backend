package dev.codeswamp.user.application.exception

import dev.codeswamp.core.common.exception.NotFoundErrorCode
import dev.codeswamp.core.common.exception.NotFoundException

enum class AppNotFoundErrorCode(
    override val code: String,
) : NotFoundErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND"),
}

open class AppNotFoundException(
    errorCode: AppNotFoundErrorCode,
    message: String
) : NotFoundException ( errorCode, message )
