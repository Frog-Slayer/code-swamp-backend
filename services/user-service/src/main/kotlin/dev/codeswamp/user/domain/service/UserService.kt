package dev.codeswamp.user.domain.service

import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username

interface UserService {
    suspend fun save(user: User) : User
    suspend fun findById(id: Long): User?
    suspend fun findUserByUsername(username: Username): User?
    suspend fun findUserByNickname(nickname: Nickname): User?
}