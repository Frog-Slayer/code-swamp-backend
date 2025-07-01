package dev.codeswamp.authcommon.token

import java.time.Instant

data class AccessTokenPayload(
    val userId: Long,
    val sub: String,
    val roles: List<String>,
    val issuedAt: Instant,
    val expiration: Instant
)