package dev.codeswamp.auth.infrastructure.security.provider

import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.auth.infrastructure.security.user.CustomUserDetails
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

class TokenAuthenticationManager(
    private val authApplication: AuthApplicationService,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val accessToken = authentication?.principal as? RawAccessToken
            ?: return Mono.error(AuthenticationServiceException("Invalid principal"))

        return mono {
            val validatedAccessToken = authApplication.validateAccessToken(accessToken)//비동기
            val userDetails = CustomUserDetails(validatedAccessToken.authUser)
            AuthenticationToken.authenticated(userDetails, null, userDetails.authorities)
        }
    }
}