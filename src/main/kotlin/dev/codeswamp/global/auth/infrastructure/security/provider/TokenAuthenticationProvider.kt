package dev.codeswamp.global.auth.infrastructure.security.provider

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.global.auth.infrastructure.security.token.Principal
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication

class TokenAuthenticationProvider(
    private val authApplication : AuthApplicationService,
): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val principal = authentication?.principal as? Principal
            ?:  throw AuthenticationServiceException("Invalid principal")

        val accessToken = when (principal) {
            is Principal.AccessToken -> principal.token
            else -> throw AuthenticationServiceException("Invalid token")
        }

        val validatedAccessToken = authApplication.validateAccessToken(accessToken)

        val userDetails = CustomUserDetails(
            validatedAccessToken.authUser
        )

        return AuthenticationToken.Companion.authenticated(userDetails, null, userDetails.authorities)
    }
    override fun supports(authentication: Class<*>?): Boolean {
        return authentication != null && AuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}