package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken

interface TokenService {
    //issue
    fun issueAccessToken(authUser: AuthUser) : ValidatedAccessToken
    fun issueRefreshToken(authUser: AuthUser) : ValidatedRefreshToken

    //store
    fun storeRefreshToken(token: ValidatedRefreshToken)
    fun rotateRefreshToken(newToken: ValidatedRefreshToken)
    fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?

    //validate
    fun validateAccessToken(accessToken: RawAccessToken) : ValidatedAccessToken
    fun validateRefreshToken(refreshToken: RawRefreshToken) : ValidatedRefreshToken

    //parse
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}