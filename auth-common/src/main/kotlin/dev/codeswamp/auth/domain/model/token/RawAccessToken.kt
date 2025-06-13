package dev.codeswamp.auth.domain.model.token

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role
import java.time.Instant

data class RawAccessToken(
    val value: String,
    val username: String,
    val userId: Long,
    val expiration: Instant,
) {
    fun isExpired() = Instant.now().isAfter(expiration)


}