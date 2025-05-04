package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.domain.user.entity.User
import dev.codeswamp.global.auth.domain.model.AuthToken

interface AuthTokenService {
    fun generateToken(user: User): AuthToken
    fun validateToken(authToken: AuthToken): Boolean
    fun extractUserInfo(authToken: AuthToken): User

    fun saveToken(userId: Long, authToken: AuthToken)
    fun findByUserId(userId: Long): AuthToken?
    fun deleteByUserId(userId: Long)
}