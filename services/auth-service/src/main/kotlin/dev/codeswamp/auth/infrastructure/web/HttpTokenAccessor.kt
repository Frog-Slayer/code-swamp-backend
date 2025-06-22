package dev.codeswamp.auth.infrastructure.web

import dev.codeswamp.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse

interface HttpTokenAccessor {
    fun extractAccessTokenFromHeader(request: ServerHttpRequest): RawAccessToken?
    fun extractRefreshToken(request: ServerHttpRequest): RawRefreshToken?
    fun injectTokenPair(response: ServerHttpResponse, tokenPair: ValidatedTokenPair)
    fun invalidateTokenPair(response: ServerHttpResponse)
}