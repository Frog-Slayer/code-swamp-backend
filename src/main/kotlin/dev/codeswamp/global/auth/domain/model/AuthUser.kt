package dev.codeswamp.global.auth.domain.model

data class AuthUser (
    val id: Long? = null,
    val username: String,
    val role: Role
)

