package dev.codeswamp.global.auth.application.service

import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpRequest
import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpResponse
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.service.AuthUserService
import dev.codeswamp.global.auth.domain.service.TokenService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class AuthApplicationService (
    private val tokenService: TokenService,
    private val authUserService: AuthUserService,
) : TokenService by tokenService,
    AuthUserService by authUserService,
{
    fun extractAccessToken(request: RawHttpRequest): RawAccessToken? {
        return request.headers["Authorization"]
            ?.removePrefix("Bearer ")
            ?.let { parseAccessToken(it) }
    }

    fun extractRefreshToken(request: RawHttpRequest): RawRefreshToken? {
        return request.cookies["refreshToken"]
            ?.let { parseRefreshToken(it) }
    }

    fun injectRefreshToken(response: RawHttpResponse, refreshToken: ValidatedRefreshToken) : RawHttpResponse {
        val updatedCookies = response.cookies.toMutableMap()
        updatedCookies["refreshToken"] = refreshToken.value
        return response.copy(cookies = updatedCookies)
    }

}