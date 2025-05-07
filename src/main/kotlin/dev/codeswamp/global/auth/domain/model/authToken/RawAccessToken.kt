package dev.codeswamp.global.auth.domain.model.authToken

import java.time.Instant

data class RawAccessToken(
    val value: String,
    val sub: String,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}