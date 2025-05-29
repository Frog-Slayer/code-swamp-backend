package dev.codeswamp.global.auth.infrastructure.security.provider

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.global.auth.infrastructure.security.token.Principal
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication

class TokenAuthenticationProvider(
    private val authApplication : AuthApplicationService,
): AuthenticationProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun authenticate(authentication: Authentication?): Authentication {
        log.debug("authenticate()")

        val accessToken = authentication?.principal as? RawAccessToken
            ?: throw AuthenticationServiceException("Invalid principal")


        log.info("Authenticate with token $accessToken")

        val validatedAccessToken = authApplication.validateAccessToken(accessToken)

        val userDetails = CustomUserDetails(
            validatedAccessToken.authUser
        )

        log.info("authenticated")

        return AuthenticationToken.authenticated(userDetails, null, userDetails.authorities)
}
    override fun supports(authentication: Class<*>?): Boolean {
        log.debug("Checking support for: ${authentication?.simpleName}")
        return authentication != null && AuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}