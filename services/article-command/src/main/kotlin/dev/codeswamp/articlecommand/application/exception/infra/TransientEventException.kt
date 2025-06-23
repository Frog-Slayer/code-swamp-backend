package dev.codeswamp.articlecommand.application.exception.infra

import dev.codeswamp.articlecommand.application.exception.application.AppInfrastructureErrorCode
import dev.codeswamp.articlecommand.application.exception.application.AppInfrastructureException
import dev.codeswamp.core.common.exception.InfrastructureErrorCode

class TransientEventException(
    message: String
): AppInfrastructureException (
    AppInfrastructureErrorCode.APP_TRANSIENT_EVENT_ERROR,
    "Transient event error. Caused by $message"
)