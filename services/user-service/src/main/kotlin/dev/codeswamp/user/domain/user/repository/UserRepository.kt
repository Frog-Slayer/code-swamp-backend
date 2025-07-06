package dev.codeswamp.user.domain.user.repository

import dev.codeswamp.user.domain.user.model.Nickname
import dev.codeswamp.user.domain.user.model.User
import dev.codeswamp.user.domain.user.model.Username

interface UserRepository {
    suspend fun insert(user: User): User
    suspend fun save(user: User): User
    suspend fun findById(id: Long): User?
    suspend fun findAllByIdsIn(ids: List<Long>): List<User>
}