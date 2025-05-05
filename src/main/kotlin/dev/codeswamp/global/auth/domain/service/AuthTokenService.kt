package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthToken
import dev.codeswamp.global.auth.domain.model.AuthUser

interface AuthTokenService {
    fun generateToken(authUser: AuthUser): AuthToken
    fun validateToken(authToken: AuthToken): Boolean
    fun extractUserInfo(authToken: AuthToken): AuthUser
}