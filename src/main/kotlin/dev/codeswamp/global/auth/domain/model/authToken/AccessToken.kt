package dev.codeswamp.global.auth.domain.model.authToken

import dev.codeswamp.global.auth.domain.model.AuthUser
import java.time.Instant

data class AccessToken(
    val value: String,
    val authUser: AuthUser,
    val expiration: Instant,
)