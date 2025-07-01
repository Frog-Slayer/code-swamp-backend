package dev.codeswamp.auth.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "auth_user")
data class AuthUserEntity(
    @Id
    val id: Long,

    @Column("email")
    val email: String,

    @Column("role")
    val role: String,
) {
    fun toDomain(): AuthUser {
        return AuthUser.of(
            id = id,
            email = email,
            roles = listOf(Role.valueOf(role)),
        )
    }

    companion object {
        fun from(authUser: AuthUser): AuthUserEntity {
            return AuthUserEntity(
                id = authUser.id,
                email = authUser.email,
                role = authUser.roles.first().name //TODO
            )
        }
    }
}