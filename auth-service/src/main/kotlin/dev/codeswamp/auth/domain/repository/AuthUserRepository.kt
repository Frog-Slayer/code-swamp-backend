package dev.codeswamp.auth.domain.repository

import dev.codeswamp.auth.domain.model.AuthUser

interface AuthUserRepository {
    suspend fun save(authUser: AuthUser): AuthUser
    suspend fun findByUsername(username: String): AuthUser?
    suspend fun findById(id: Long): AuthUser?
}