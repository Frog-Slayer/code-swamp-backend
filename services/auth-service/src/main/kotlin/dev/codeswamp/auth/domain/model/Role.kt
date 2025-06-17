package dev.codeswamp.auth.domain.model

enum class Role {
    GUEST,
    USER,
    ADMIN;

    companion object {
        fun from(roleString: String): Role? {
            return entries.find { it.name == roleString.uppercase() }
        }
    }
}