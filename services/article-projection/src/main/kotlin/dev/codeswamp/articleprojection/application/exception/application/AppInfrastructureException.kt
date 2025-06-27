package dev.codeswamp.articleprojection.application.exception.application

import dev.codeswamp.core.common.exception.InfrastructureErrorCode
import dev.codeswamp.core.common.exception.InfrastructureException
import dev.codeswamp.core.common.exception.NotFoundErrorCode
import dev.codeswamp.core.common.exception.NotFoundException

enum class AppInfrastructureErrorCode(
    override val code: String,
) : InfrastructureErrorCode {
    APP_INFRA_ERROR("APP_INFRA_ERROR"),
    APP_TRANSIENT_EVENT_ERROR("APP_TRANSIENT_EVENT_ERROR"),
}

open class AppInfrastructureException(
    errorCode: InfrastructureErrorCode,
    message: String
) : InfrastructureException (errorCode, message)