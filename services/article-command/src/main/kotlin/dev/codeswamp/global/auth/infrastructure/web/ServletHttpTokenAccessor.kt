package dev.codeswamp.global.auth.infrastructure.web

import dev.codeswamp.global.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServletHttpTokenAccessor (
    private val authApplicationService: AuthApplicationService,
    @Value("\${jwt.access-token-exp}") private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-token-exp}") private val refreshTokenExpiration: Long,
) : HttpTokenAccessor {

    override fun extractAccessTokenFromHeader(request: HttpServletRequest): RawAccessToken? {
        return request.getHeader("Authorization")
            ?.removePrefix("Bearer ")
            ?.let { authApplicationService.parseAccessToken(it) }
    }

    override fun extractRefreshToken(request: HttpServletRequest): RawRefreshToken? {
        return request.cookies?.find { it.name == "refresh_token"}
            ?.value
            ?.let { authApplicationService.parseRefreshToken(it) }
    }

    override fun injectTokenPair(response: HttpServletResponse, tokenPair: ValidatedTokenPair) {
        val accessToken = Cookie("access_token", tokenPair.accessToken.value).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = accessTokenExpiration.toInt()
        }

        val refreshToken = Cookie("refresh_token", tokenPair.refreshToken.value).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = refreshTokenExpiration.toInt()
        }

        response.addCookie(refreshToken)
        response.addCookie(accessToken)
    }

    override fun invalidateTokenPair(response: HttpServletResponse) {
        val refreshToken = Cookie("refresh_token", null).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = 0
        }

        val accessToken = Cookie("access_token", null).apply {
            path = "/"
            isHttpOnly = true
            secure = true
            maxAge = 0
        }

        response.addCookie(accessToken)
        response.addCookie(refreshToken)
    }
}