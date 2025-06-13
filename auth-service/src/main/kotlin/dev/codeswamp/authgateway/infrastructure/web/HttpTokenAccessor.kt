package dev.codeswamp.authgateway.infrastructure.web

import dev.codeswamp.authgateway.application.dto.ValidatedTokenPair
import dev.codeswamp.authgateway.domain.model.token.RawAccessToken
import dev.codeswamp.authgateway.domain.model.token.RawRefreshToken
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse

interface HttpTokenAccessor {
    fun extractAccessTokenFromHeader(request: ServerHttpRequest): RawAccessToken?
    fun extractRefreshToken(request: ServerHttpRequest): RawRefreshToken?
    fun injectTokenPair(response: ServerHttpResponse, tokenPair: ValidatedTokenPair)
    fun invalidateTokenPair(response: ServerHttpResponse)
}