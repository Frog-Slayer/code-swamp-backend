package dev.codeswamp.user.presentation.exception

import dev.codeswamp.core.common.exception.BaseException

data class ErrorResponse private constructor(
    val code: String,
    val message: String
) {
    companion object {
        fun from(exception: BaseException) = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )
    }
}