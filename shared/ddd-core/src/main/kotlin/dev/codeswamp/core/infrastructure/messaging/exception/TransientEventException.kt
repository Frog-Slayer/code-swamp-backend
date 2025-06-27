package dev.codeswamp.core.infrastructure.messaging.exception

import dev.codeswamp.core.common.exception.InfrastructureErrorCode
import dev.codeswamp.core.common.exception.InfrastructureException

enum class TransientEventErrorCode (
    override val code: String,
) : InfrastructureErrorCode{
    TRANSIENT_EVENT_ERROR("TRANSIENT_EVENT_ERROR"),
}

class TransientEventException(
    message: String
):  InfrastructureException(
    TransientEventErrorCode.TRANSIENT_EVENT_ERROR,
    "Transient event error. Caused by $message"
)