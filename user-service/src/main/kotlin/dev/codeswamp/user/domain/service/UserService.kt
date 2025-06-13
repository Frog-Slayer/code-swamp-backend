package dev.codeswamp.user.domain.service

import dev.codeswamp.user.domain.model.Nickname
import dev.codeswamp.user.domain.model.User
import dev.codeswamp.user.domain.model.Username

interface UserService {
    fun save(user: User) : User
    fun findById(id: Long): User?
    fun findUserByUsername(username: Username): User?
    fun findUserByNickname(nickname: Nickname): User?
}