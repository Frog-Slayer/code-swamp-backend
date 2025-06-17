package dev.codeswamp.auth.domain.model.token

import java.time.Instant

data class RawRefreshToken (
    val value: String,
    val sub: String,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}

