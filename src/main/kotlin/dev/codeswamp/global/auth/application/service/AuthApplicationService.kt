package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpRequest
import dev.codeswamp.global.auth.application.dto.RawHttpResponse
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import org.springframework.stereotype.Service

@Service
class AuthApplicationService{

    fun extractAccessToken(request: RawHttpRequest): String? {
        return request.headers["Authorization"]?.removePrefix("Bearer ")
    }

    fun extractRefreshToken(request: RawHttpRequest): String? {
        return request.cookies["refreshToken"]
    }

    fun injectRefreshToken(response: RawHttpResponse, refreshToken: ValidatedRefreshToken) : RawHttpResponse {
        val updatedCookies = response.cookies.toMutableMap()
        updatedCookies["refreshToken"] = refreshToken.value
        return response.copy(cookies = updatedCookies)
    }
}