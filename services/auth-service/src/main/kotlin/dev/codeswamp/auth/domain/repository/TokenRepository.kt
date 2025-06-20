package dev.codeswamp.auth.domain.repository

import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken

interface TokenRepository {
    suspend fun storeRefreshToken(refreshToken: ValidatedRefreshToken)
    suspend fun delete(refreshToken: ValidatedRefreshToken)

    suspend fun findRefreshTokenByToken(token: String): ValidatedRefreshToken?
    suspend fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?
}