package dev.codeswamp.core.user.domain.repository

import dev.codeswamp.core.user.domain.model.User

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User
    fun findByUsername(username: String): User
    fun findByEmail(email: String): User
    fun findByNickname(nickname: String): User
}