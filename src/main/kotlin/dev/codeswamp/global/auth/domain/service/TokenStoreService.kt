package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken

interface TokenStoreService {
    fun storeRefreshToken(token: RefreshToken)
    fun rotateRefreshToken(token: String, newToken: RefreshToken)
    fun findRefreshTokenByUserId(userId: Long): RefreshToken?
}