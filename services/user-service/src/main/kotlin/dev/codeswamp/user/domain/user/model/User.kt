package dev.codeswamp.user.domain.user.model

import dev.codeswamp.core.domain.AggregateRoot
import dev.codeswamp.core.domain.DomainEvent
import dev.codeswamp.user.domain.user.event.UserRegisteredEvent

data class User private constructor(
    val id: Long,
    val username: Username,//변경 불가(사용자 ID)
    val nickname: Nickname,
    val profileImage: String? = null,
) : AggregateRoot() {
    companion object {
        fun of(
            id: Long,
            username: String,
            nickname: String,
            profileImage: String? = null
        ) = User(
            id = id,
            username = Username.of(username),
            nickname = Nickname.of(nickname),
            profileImage = profileImage
        )
    }

    fun modifyNickname(nickname: String): User {
        return this.copy(
            nickname = Nickname.of(nickname)
        )
    }

    fun modifyProfileImage(profileImage: String?): User {
        return this.copy(
            profileImage = profileImage
        )
    }

    fun registered(): User {
        return this.withEvent(
            UserRegisteredEvent(
                userId = id,
                username = username.value
            )
        )
    }

    fun withEvent(event: DomainEvent): User {
        val copy = this.copy()
        copy.addEvent(event)
        return copy
    }
}