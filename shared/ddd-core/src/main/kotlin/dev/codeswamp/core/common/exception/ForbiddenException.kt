package dev.codeswamp.core.common.exception

interface ForbiddenErrorCode : ErrorCode

abstract class ForbiddenException(
    errorCode: ForbiddenErrorCode,
    message: String
) : BaseException(errorCode, message)