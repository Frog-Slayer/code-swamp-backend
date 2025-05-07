package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken

interface TokenStoreService {
    fun storeRefreshToken(token: ValidatedRefreshToken)
    fun rotateRefreshToken(token: String, newToken: ValidatedRefreshToken)
    fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?
}