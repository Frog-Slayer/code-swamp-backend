package dev.codeswamp.projection.application.exception.infra

import dev.codeswamp.projection.application.exception.application.AppInfrastructureErrorCode
import dev.codeswamp.projection.application.exception.application.AppInfrastructureException

class TransientEventException(
    message: String
): AppInfrastructureException (
    AppInfrastructureErrorCode.APP_TRANSIENT_EVENT_ERROR,
    "Transient event error. Caused by $message"
)