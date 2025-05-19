package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpRequest
import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpResponse
import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class AuthApplicationService (
   private val tokenService: TokenService,
) {

    fun extractAccessToken(request: RawHttpRequest): RawAccessToken? {
        return request.headers["Authorization"]
            ?.removePrefix("Bearer ")
            ?.let { tokenService.parseAccessToken(it) }
    }

    fun extractRefreshToken(request: RawHttpRequest): RawRefreshToken? {
        return request.cookies["refreshToken"]
            ?.let { tokenService.parseRefreshToken(it) }
    }

    fun injectRefreshToken(response: RawHttpResponse, refreshToken: ValidatedRefreshToken) : RawHttpResponse {
        val updatedCookies = response.cookies.toMutableMap()
        updatedCookies["refreshToken"] = refreshToken.value
        return response.copy(cookies = updatedCookies)
    }

}