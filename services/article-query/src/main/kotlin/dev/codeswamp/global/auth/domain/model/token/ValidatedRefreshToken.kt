package dev.codeswamp.global.auth.domain.model.token

import dev.codeswamp.global.auth.domain.model.AuthUser
import java.time.Instant

data class ValidatedRefreshToken (
    val value: String,
    val authUser: AuthUser,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}

