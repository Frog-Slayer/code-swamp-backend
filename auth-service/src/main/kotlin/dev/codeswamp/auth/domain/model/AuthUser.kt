package dev.codeswamp.auth.domain.model

data class AuthUser (
    val id: Long? = null,
    val username: String,
    val roles: List<Role>
)

