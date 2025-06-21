package dev.codeswamp.articlecommand.presentation.exception

import dev.codeswamp.articlecommand.application.exception.application.AppException
import dev.codeswamp.articlecommand.domain.exception.domain.DomainException
import dev.codeswamp.articlecommand.infrastructure.exception.infrastructure.InfraException

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