package dev.codeswamp.authcommon.exception

class InvalidTokenException(
    message: String,
) : RuntimeException("Invalid token: $message")