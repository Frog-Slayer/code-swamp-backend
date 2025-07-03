package dev.codeswamp.authcommon.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(
    message: String,
) : AuthenticationException("Invalid token: $message")