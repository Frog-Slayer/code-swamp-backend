package dev.codeswamp.user.infrastructure.persistence.entity

import dev.codeswamp.user.domain.user.model.Nickname
import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.model.Username
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val username: String,

    @Column(unique = true)
    var nickname: String,

    var profileUrl: String? = null,
) {
    fun toDomain(): User {
        return User(
            id = id,
            username = Username.Companion.of(username),
            nickname = Nickname.Companion.of(nickname),
            profileUrl = profileUrl,
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                username = user.username.value,
                nickname = user.nickname.value,
                profileUrl = user.profileUrl,
            )
        }
    }
}