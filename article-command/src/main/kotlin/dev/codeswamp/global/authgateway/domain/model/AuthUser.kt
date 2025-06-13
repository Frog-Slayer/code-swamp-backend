package dev.codeswamp.global.authgateway.domain.model

data class AuthUser (
    val id: Long? = null,
    val username: String,
    val role: Role
)

