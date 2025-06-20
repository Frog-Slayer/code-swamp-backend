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
    val id: Long,

    @Column(unique = true)
    val username: String,

    @Column(unique = true)
    var nickname: String,

    var profileImage: String? = null,
) {
    fun toDomain(): User {
        return User.of(
            id = id,
            username = username,
            nickname = nickname,
            profileImage = profileImage,
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                username = user.username.value,
                nickname = user.nickname.value,
                profileImage = user.profileImage,
            )
        }
    }
}