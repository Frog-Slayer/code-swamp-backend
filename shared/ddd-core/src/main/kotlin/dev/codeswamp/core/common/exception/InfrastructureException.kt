package dev.codeswamp.core.common.exception

interface InfrastructureErrorCode: ErrorCode

abstract class InfrastructureException(
    errorCode: InfrastructureErrorCode,
    message: String
) : BaseException(errorCode, message)