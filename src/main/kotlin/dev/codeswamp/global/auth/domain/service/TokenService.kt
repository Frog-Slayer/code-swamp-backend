package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken

interface TokenService {
    //issue
    fun issueAccessToken(authUser: AuthUser) : ValidatedAccessToken
    fun issueRefreshToken(authUser: AuthUser) : ValidatedRefreshToken

    //store
    fun storeRefreshToken(token: ValidatedRefreshToken)
    fun rotateRefreshToken(token: String, newToken: ValidatedRefreshToken)
    fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?

    //validate
    fun validateAccessToken(accessToken: RawAccessToken) : ValidatedAccessToken
    fun validateRefreshToken(refreshToken: RawRefreshToken) : ValidatedRefreshToken
}