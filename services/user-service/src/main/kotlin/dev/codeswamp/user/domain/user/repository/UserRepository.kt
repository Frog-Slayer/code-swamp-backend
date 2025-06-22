package dev.codeswamp.user.domain.user.repository

import dev.codeswamp.user.domain.user.model.Nickname
import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.model.Username

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByUsername(username: Username): User?
    fun findByNickname(nickname: Nickname): User?
}