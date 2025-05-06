package dev.codeswamp.core.user.domain.model

import dev.codeswamp.core.user.infrastructure.entity.Role

data class User (
    val id: Long? = null,
    val username: String,
    val email: String,
    val nickname: String,
    val profileUrl: String,
    val role: Role,
)