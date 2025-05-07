package dev.codeswamp.global.auth.domain.repository

import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken

interface TokenRepository {
    fun storeRefreshToken(refreshToken: ValidatedRefreshToken)
    fun findRefreshTokenByToken(token: String): ValidatedRefreshToken?
    fun findRefreshTokenByUserId(userId: String): ValidatedRefreshToken?
}