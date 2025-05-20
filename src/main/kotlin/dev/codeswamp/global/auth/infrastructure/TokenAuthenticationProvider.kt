package dev.codeswamp.global.auth.infrastructure

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

class TokenAuthenticationProvider(
    private val authApplication : AuthApplicationService,
): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        val rawAccessToken = authentication?.principal as? RawAccessToken ?: throw AuthenticationServiceException("cannot ")
        val validatedAccessToken = authApplication.validateAccessToken(rawAccessToken)




    }

    private fun retrieveUser(rawAccessToken: RawAccessToken?): UserDetails? {
        return CustomUserDetails (

        )

    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication != null && AuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}