package dev.codeswamp.global.auth.infrastructure.security.filter

import dev.codeswamp.global.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationFilter(
    private val requestMatcher: RequestMatcher,
    private val httpTokenAccessor: HttpTokenAccessor
) : AbstractAuthenticationProcessingFilter(requestMatcher){

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication? {
        val rawAccessToken = httpTokenAccessor.extractAccessToken(request) ?: throw AuthenticationServiceException(
            "cannot extract access token"
        )

        val preAuthenticated = AuthenticationToken.Companion.unAuthenticated(rawAccessToken, null)
        return authenticationManager.authenticate(preAuthenticated)
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