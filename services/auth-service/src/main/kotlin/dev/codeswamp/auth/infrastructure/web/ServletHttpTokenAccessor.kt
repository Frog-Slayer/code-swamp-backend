package dev.codeswamp.auth.infrastructure.web

import dev.codeswamp.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component

@Component
class ServletHttpTokenAccessor(
    private val authApplicationService: AuthApplicationService,
    @Value("\${jwt.access-token-exp}") private val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-token-exp}") private val refreshTokenExpiration: Long,
) : HttpTokenAccessor {

    override fun extractAccessTokenFromHeader(request: ServerHttpRequest): RawAccessToken? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
            ?.let { authApplicationService.parseAccessToken(it) }
    }

    override fun extractRefreshToken(request: ServerHttpRequest): RawRefreshToken? {
        return request.cookies.getFirst("refresh_token")
            ?.value
            ?.let { authApplicationService.parseRefreshToken(it) }
    }

    override fun injectTokenPair(
        response: ServerHttpResponse,
        tokenPair: ValidatedTokenPair
    ) {
        val accessToken = ResponseCookie.from("access_token", tokenPair.accessToken.value)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(accessTokenExpiration)
            .build()

        val refreshToken = ResponseCookie.from("refresh_token", tokenPair.refreshToken.value)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(refreshTokenExpiration)
            .build()

        response.addCookie(refreshToken)
        response.addCookie(accessToken)
    }

    override fun invalidateTokenPair(response: ServerHttpResponse) {
        val accessToken = ResponseCookie.from("access_token", "")
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(0)
            .build()

        val refreshToken = ResponseCookie.from("refresh_token", "")
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(0)
            .build()

        response.addCookie(refreshToken)
        response.addCookie(accessToken)
    }
}