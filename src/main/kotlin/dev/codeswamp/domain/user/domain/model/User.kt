package dev.codeswamp.domain.user.domain.model

import dev.codeswamp.domain.user.entity.Role

data class User (
    val id: Long? = null,
    val username: String,
    val email: String,
    val nickname: String,
    val profileUrl: String,
    val role: Role,
)