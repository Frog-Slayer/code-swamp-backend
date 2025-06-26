package dev.codeswamp.core.common.exception

interface NotFoundErrorCode : ErrorCode

abstract class NotFoundException(
    errorCode: NotFoundErrorCode,
    message: String
) : BaseException(errorCode, message)