package dev.codeswamp.user.infrastructure.persistence.entity

import dev.codeswamp.user.domain.user.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "users")
data class UserEntity(
    @Id
    val id: Long,

    @Column("username")
    val username: String,

    @Column("nickname")
    var nickname: String,

    @Column("profile_image")
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