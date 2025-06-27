package dev.codeswamp.articleprojection.application.exception.infra

import dev.codeswamp.articleprojection.application.exception.application.AppInfrastructureErrorCode
import dev.codeswamp.articleprojection.application.exception.application.AppInfrastructureException

class TransientEventException(
    message: String
): AppInfrastructureException (
    AppInfrastructureErrorCode.APP_TRANSIENT_EVENT_ERROR,
    "Transient event error. Caused by $message"
)