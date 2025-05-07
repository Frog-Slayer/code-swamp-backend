package dev.codeswamp.global.auth.domain.model.authToken

import dev.codeswamp.global.auth.domain.model.AuthUser
import java.time.Instant

data class RawRefreshToken (
    val value: String,
    val sub: String,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}

