package dev.codeswamp.global.auth.infrastructure.web

import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface HttpTokenAccessor {
    fun extractAccessToken(request: HttpServletRequest): RawAccessToken?
    fun extractRefreshToken(request: HttpServletRequest): RawRefreshToken?
    fun injectRefreshToken(response: HttpServletResponse, refreshToken: ValidatedRefreshToken)

}