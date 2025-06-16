package dev.codeswamp.core.user.domain.repository

import dev.codeswamp.core.user.domain.model.Nickname
import dev.codeswamp.core.user.domain.model.User
import dev.codeswamp.core.user.domain.model.Username
import java.util.Optional

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByUsername(username: Username): User?
    fun findByNickname(nickname: Nickname): User?
}