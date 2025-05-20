package dev.codeswamp.global.auth.infrastructure

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.infrastructure.web.ServletRawHttpMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationFilter(
    private val requestMatcher: RequestMatcher,
    private val authApplicationService: AuthApplicationService
) : AbstractAuthenticationProcessingFilter(requestMatcher){

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication? {
        val rawAccessToken = authApplicationService.extractAccessToken(ServletRawHttpMapper.toRawRequest(request))

        val preAuthenticated = AuthenticationToken.unAuthenticated(rawAccessToken, null)
        return this.authenticationManager().authenticate(preAuthenticated)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }

}