package dev.codeswamp.authgateway.domain.service

import dev.codeswamp.authgateway.domain.model.AuthUser
import dev.codeswamp.authgateway.domain.model.token.RawAccessToken
import dev.codeswamp.authgateway.domain.model.token.RawRefreshToken
import dev.codeswamp.authgateway.domain.model.token.ValidatedAccessToken
import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken

interface TokenService {
    //issue
    fun issueAccessToken(authUser: AuthUser) : ValidatedAccessToken
    fun issueRefreshToken(authUser: AuthUser) : ValidatedRefreshToken

    //store
    suspend fun storeRefreshToken(token: ValidatedRefreshToken)
    suspend fun rotateRefreshToken(newToken: ValidatedRefreshToken)
    suspend fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken?
    suspend fun deleteRefreshTokenByUserId(userId: Long)

    //validate
    suspend fun validateAccessToken(accessToken: RawAccessToken) : ValidatedAccessToken
    suspend fun validateRefreshToken(refreshToken: RawRefreshToken) : ValidatedRefreshToken

    //parse
    fun parseAccessToken(accessToken: String): RawAccessToken
    fun parseRefreshToken(refreshToken: String): RawRefreshToken
}