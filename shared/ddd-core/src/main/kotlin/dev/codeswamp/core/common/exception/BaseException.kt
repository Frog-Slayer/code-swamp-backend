package dev.codeswamp.core.common.exception

interface ErrorCode {
    val code: String
}

abstract class BaseException (
    val errorCode: ErrorCode,
    message: String
) : RuntimeException(message)
