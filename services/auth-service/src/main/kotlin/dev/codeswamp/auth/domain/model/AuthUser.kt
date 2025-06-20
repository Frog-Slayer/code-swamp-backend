package dev.codeswamp.auth.domain.model

data class AuthUser private constructor(
    val id: Long,
    val email: String,
    val roles: List<Role>
) {
    companion object {
        fun of(id: Long, email: String, roles: List<Role>) = AuthUser (
            id = id,
            email = email,
            roles = roles
        )

        fun createUser( generateId : () -> Long, email : String) = AuthUser (
            id = generateId(),
            email = email,
            roles = listOf(Role.USER)
        )
    }




}

