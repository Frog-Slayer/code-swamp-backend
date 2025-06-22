package dev.codeswamp.auth.domain.model.token

import java.time.Instant

data class RawAccessToken(
    val value: String,
    val username: String,
    val userId: Long,
    val roles: List<String>,
    val expiration: Instant,
) {
    fun expired() = Instant.now().isAfter(expiration)
}