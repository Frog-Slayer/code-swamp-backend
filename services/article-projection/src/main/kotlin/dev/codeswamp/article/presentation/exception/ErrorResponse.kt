package dev.codeswamp.article.presentation.exception

import dev.codeswamp.article.application.exception.application.AppException
import dev.codeswamp.article.domain.exception.domain.DomainException
import dev.codeswamp.article.infrastructure.exception.infrastructure.InfraException

data class ErrorResponse private constructor(
    val code: String,
    val message: String
) {
    companion object {
        fun from(exception: DomainException) = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )

        fun from(exception: AppException) = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )

        fun from(exception: InfraException) = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )
    }
}