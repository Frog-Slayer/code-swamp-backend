package dev.codeswamp.global.authgateway.domain.model.token

import dev.codeswamp.global.authgateway.domain.model.AuthUser
import java.time.Instant

data class ValidatedAccessToken(
    val value: String,
    val authUser: AuthUser,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}