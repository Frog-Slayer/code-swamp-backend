package dev.codeswamp.authgateway.domain.repository

import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken

interface TokenRepository {
    fun storeRefreshToken(refreshToken: ValidatedRefreshToken)
    fun delete(refreshToken: ValidatedRefreshToken)

    fun findRefreshTokenByToken(token: String): ValidatedRefreshToken?
    fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?
}