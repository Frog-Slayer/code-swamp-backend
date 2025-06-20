package dev.codeswamp.auth.domain.model

data class AuthUser private constructor(
    val id: Long,
    val email: String,
    val roles: List<Role>
) {
    companion object {
        fun createUser( generateId : () -> Long, email : String) = AuthUser (
            id = generateId(),
            email = email,
            roles = listOf(Role.USER)
        )
    }




}

