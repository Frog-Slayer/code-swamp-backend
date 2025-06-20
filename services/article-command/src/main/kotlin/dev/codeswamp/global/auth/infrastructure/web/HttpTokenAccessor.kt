package dev.codeswamp.global.auth.infrastructure.web

import dev.codeswamp.global.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface HttpTokenAccessor {
    fun extractAccessTokenFromHeader(request: HttpServletRequest): RawAccessToken?
    fun extractRefreshToken(request: HttpServletRequest): RawRefreshToken?

    fun injectTokenPair(response: HttpServletResponse, tokenPair: ValidatedTokenPair)

    fun invalidateTokenPair(response: HttpServletResponse)
}