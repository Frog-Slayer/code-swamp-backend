package dev.codeswamp.global.authgateway.domain.repository

import dev.codeswamp.global.authgateway.domain.model.token.ValidatedRefreshToken

interface TokenRepository {
    fun storeRefreshToken(refreshToken: ValidatedRefreshToken)
    fun delete(refreshToken: ValidatedRefreshToken)

    fun findRefreshTokenByToken(token: String): ValidatedRefreshToken?
    fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?
}