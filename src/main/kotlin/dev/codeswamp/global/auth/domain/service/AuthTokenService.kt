package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken

interface AuthTokenService {
    fun generateAccessToken(authUser: AuthUser): AccessToken
    fun generateRefreshToken(authUser: AuthUser): RefreshToken

    fun validateToken(authToken: AccessToken): Boolean
    fun extractAuthUserFrom(authToken: AccessToken): AuthUser
}