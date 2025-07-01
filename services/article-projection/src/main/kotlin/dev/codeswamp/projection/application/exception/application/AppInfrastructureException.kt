package dev.codeswamp.projection.application.exception.application

import dev.codeswamp.core.common.exception.InfrastructureErrorCode
import dev.codeswamp.core.common.exception.InfrastructureException

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