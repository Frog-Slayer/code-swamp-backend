package dev.codeswamp.global.auth.infrastructure.web

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServletHttpTokenAccessor (
    private val authApplicationService: AuthApplicationService,
    @Value("\${jwt.access-token-exp}") private val refreshTokenExpiration: Long,
) : HttpTokenAccessor {

    override fun extractAccessToken(request: HttpServletRequest): RawAccessToken? {
        return request.getHeader("Authorization")
            ?.removePrefix("Bearer ")
            ?.let { authApplicationService.parseAccessToken(it) }
    }

    override fun extractRefreshToken(request: HttpServletRequest): RawRefreshToken? {
        return request.cookies?.find { it.name == "refresh_token"}
            ?.value
            ?.let { authApplicationService.parseRefreshToken(it) }
    }

    override fun injectRefreshToken(response: HttpServletResponse, refreshToken: ValidatedRefreshToken) {
        val cookie = Cookie("refresh_token", refreshToken.value).apply {
            path = "/"
            isHttpOnly = true
            secure = false //TODO
            maxAge = refreshTokenExpiration.toInt()
        }

        response.addCookie(cookie)
        println(response)
    }
}