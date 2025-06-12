package dev.codeswamp.global.auth.domain.repository

import dev.codeswamp.global.auth.domain.model.AuthUser

interface AuthUserRepository {
    fun save(authUser: AuthUser): AuthUser
    fun findByUsername(username: String): AuthUser?
    fun findById(id: Long): AuthUser?
}