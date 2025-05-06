package dev.codeswamp.core.user.infrastructure.persistence.entity

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.Role
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
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

    @Column(nullable = false, unique = true)
    val username: String,//변경 불가(사용자 ID)

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, unique = true)
    var nickname: String,

    var profileUrl: String? = null,

    //TODO
    /**
    @Column(nullable = false)
    var role: Role = Role.ROLE_GUEST,
    **/
) {
    fun toDomain(): User {
        return User(
            id = id,
            username = Username.of(username),
            email = email,
            nickname = Nickname.of(username),
            profileUrl = profileUrl,
            role = Role.GUEST
        )
    }





}