package dev.codeswamp.core.article.presentation.exception

import dev.codeswamp.core.article.application.exception.application.AppException
import dev.codeswamp.core.article.domain.exception.domain.DomainException
import dev.codeswamp.core.article.infrastructure.exception.infrastructure.InfraException

data class ErrorResponse private constructor(
    val code: String,
    val message: String
) {
    companion object {
        fun from(exception: DomainException) =  ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )

        fun from(exception: AppException) =  ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )

        fun from(exception: InfraException) = ErrorResponse(
            code = exception.errorCode.code,
            message = exception.message.toString()
        )
    }
}