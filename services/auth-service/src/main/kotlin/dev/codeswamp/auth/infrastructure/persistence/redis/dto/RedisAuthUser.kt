package dev.codeswamp.auth.infrastructure.persistence.redis.dto

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role

data class RedisAuthUser (
    val id: Long,
    val email: String,
    val roles: List<Role>
) {
    companion object {
        fun from(authUser: AuthUser) = RedisAuthUser(
            id = authUser.id,
            email = authUser.email,
            roles = authUser.roles
        )
    }

    fun toDomain() = AuthUser.of(
        id = id,
        email = email,
        roles = roles
    )
}