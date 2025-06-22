package dev.codeswamp.auth.infrastructure.persistence.redis.dto

import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import java.time.Instant

data class RedisValidatedRefreshToken(
    val value: String,
    val authUser: RedisAuthUser,
    val expiration: Instant,
) {
    companion object {
        fun from(validatedRefreshToken: ValidatedRefreshToken) = RedisValidatedRefreshToken(
            value = validatedRefreshToken.value,
            authUser = RedisAuthUser.from(validatedRefreshToken.authUser),
            expiration = validatedRefreshToken.expiration,
        )
    }

    fun toDomain() = ValidatedRefreshToken(
        value = value,
        authUser = authUser.toDomain(),
        expiration = expiration
    )
}