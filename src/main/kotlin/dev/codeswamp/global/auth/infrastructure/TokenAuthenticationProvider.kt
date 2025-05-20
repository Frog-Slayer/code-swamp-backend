package dev.codeswamp.global.auth.infrastructure

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

class TokenAuthenticationProvider(
    private val authApplication : AuthApplicationService,
): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        val rawAccessToken = authentication?.principal as? RawAccessToken

        return AuthenticationToken(

        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication != null && AuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}