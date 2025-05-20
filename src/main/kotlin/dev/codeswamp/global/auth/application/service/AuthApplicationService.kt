package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.service.AuthUserService
import dev.codeswamp.global.auth.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class AuthApplicationService (
    private val tokenService: TokenService,
    private val authUserService: AuthUserService,
) : TokenService by tokenService,
    AuthUserService by authUserService
{

    fun refresh(rawRefreshToken: RawRefreshToken) : ValidatedTokenPair {
        val oldRefreshToken = validateRefreshToken(rawRefreshToken)
        val authUser = oldRefreshToken.authUser

        val newRefreshToken = issueRefreshToken(authUser)
        tokenService.rotateRefreshToken(newRefreshToken)

        val newAccessToken = issueAccessToken(authUser)

        return ValidatedTokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }
}