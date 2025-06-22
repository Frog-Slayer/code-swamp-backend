package dev.codeswamp.authcommon.token

import java.time.Instant

data class AccessTokenPayload (
    val userId: Long,
    val sub: String,
    val role: List<String>,
    val issuedAt: Instant,
    val expiration: Instant
)