package dev.codeswamp.global.auth.infrastructure.security.filter

import dev.codeswamp.global.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

class TokenAuthenticationFilter(
    requestMatcher: RequestMatcher,
    private val httpTokenAccessor: HttpTokenAccessor
) : AbstractAuthenticationProcessingFilter(requestMatcher){
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication? {
        log.info("attemptAuthentication()")

        val rawAccessToken = httpTokenAccessor.extractAccessToken(request)
            ?: throw AuthenticationServiceException("cannot extract access token")

        log.info("extracted access token {}", rawAccessToken.value)

        val preAuthenticated = AuthenticationToken.unAuthenticated(rawAccessToken, null)

        return authenticationManager.authenticate(preAuthenticated)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication?
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }

}