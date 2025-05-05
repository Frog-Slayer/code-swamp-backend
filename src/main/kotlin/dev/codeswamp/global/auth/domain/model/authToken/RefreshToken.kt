package dev.codeswamp.global.auth.domain.model.authToken

import java.time.Instant

data class RefreshToken (
    val value: String,
    val expiration: Instant,
) {
    fun isExpired(): Boolean {
        return Instant.now().isAfter(expiration)
    }
}

