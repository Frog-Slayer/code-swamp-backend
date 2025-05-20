package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
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


    fun refresh(rawRefreshToken: RawRefreshToken) : ValidatedRefreshToken {
        val oldToken = tokenService.validateRefreshToken(rawRefreshToken)
        val authUser = oldToken.authUser

        val newToken = tokenService.issueRefreshToken(authUser)

        tokenService.rotateRefreshToken(newToken)
        return newToken;
    }
}