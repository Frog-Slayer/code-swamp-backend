package dev.codeswamp.core.common.exception

interface ConflictErrorCode : ErrorCode

abstract class ConflictException(
    errorCode: ConflictErrorCode,
    message: String
) : BaseException(errorCode, message)