package dev.codeswamp.global.authgateway.domain.repository

import dev.codeswamp.global.authgateway.domain.model.AuthUser

interface AuthUserRepository {
    fun save(authUser: AuthUser): AuthUser
    fun findByUsername(username: String): AuthUser?
    fun findById(id: Long): AuthUser?
}