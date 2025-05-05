package dev.codeswamp.global.auth.domain.model

data class AuthUser (
    val id: Long,
    val username: String,
    val roles: Set<String>,
)

