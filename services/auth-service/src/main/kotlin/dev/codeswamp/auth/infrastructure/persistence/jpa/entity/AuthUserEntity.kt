package dev.codeswamp.auth.infrastructure.persistence.jpa.entity

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity
data class AuthUserEntity(
    @Id
    val id: Long,

    @Column(unique = true)
    val username: String,

    //TODO: 권한 생각해보고 -> 리스트로 관리할 필요가 있는 경우 별도로 분리할 것
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    val role : Role
) {
    fun toDomain(): AuthUser {
        return AuthUser.of(
            id = id,
            email = username,
            roles = listOf(role)
        )
    }

    companion object {
        fun from(authUser: AuthUser): AuthUserEntity {
            return AuthUserEntity(
                id = authUser.id,
                username = authUser.email,
                role = authUser.roles.first() //TODO
            )
        }
    }
}