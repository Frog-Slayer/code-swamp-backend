package dev.codeswamp.core.user.domain.service

import dev.codeswamp.core.user.domain.model.User

interface UserService {
    fun save(user: User)

    fun findById(id: Long): User
    fun findUserByEmail(email: String): User
    fun findUserByUsername(username: String): User
    fun findUserByNickname(nickname: String): User

    fun isValidUsername(username: String): Boolean
    fun isValidNickname(nickname: String): Boolean
}