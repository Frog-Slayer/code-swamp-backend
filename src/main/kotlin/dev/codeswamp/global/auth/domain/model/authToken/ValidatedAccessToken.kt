package dev.codeswamp.global.auth.domain.model.authToken

import dev.codeswamp.global.auth.domain.model.AuthUser
import java.time.Instant

data class ValidatedAccessToken(
    val value: String,
    val authUser: AuthUser,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}