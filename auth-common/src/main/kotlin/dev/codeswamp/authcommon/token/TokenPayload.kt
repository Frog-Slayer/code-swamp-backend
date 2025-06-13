package dev.codeswamp.authcommon.token

import java.time.Instant

data class TokenPayload (
    val userId: Long? = null,
    val username: String,
    val role: List<String>,
    val issuedAt: Instant,
    val expiration: Instant
)