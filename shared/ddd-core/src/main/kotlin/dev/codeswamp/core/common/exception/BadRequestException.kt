package dev.codeswamp.core.common.exception

interface BadRequestErrorCode: ErrorCode

abstract class BadRequestException (
    errorCode: BadRequestErrorCode,
    message: String
) : BaseException(errorCode, message)