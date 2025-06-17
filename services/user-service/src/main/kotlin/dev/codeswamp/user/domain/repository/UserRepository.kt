package dev.codeswamp.user.domain.repository

import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByUsername(username: Username): User?
    fun findByNickname(nickname: Nickname): User?
}