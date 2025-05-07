package dev.codeswamp.global.auth.domain.repository

import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken

interface TokenRepository {
    fun storeRefreshToken(refreshToken: RefreshToken)
    fun findRefreshTokenByToken(token: String): RefreshToken?
    fun findRefreshTokenByUserId(userId: String): RefreshToken?
}