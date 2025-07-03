package dev.codeswamp.authcommon.security

import dev.codeswamp.authcommon.exception.InvalidTokenException
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import reactor.core.publisher.Mono

class TokenAuthenticationManager(
    private val jwtAccessTokenParser: JwtAccessTokenParser,
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val accessToken = authentication?.principal as? String
            ?: return Mono.error(AuthenticationServiceException("Invalid principal"))

        return mono {
            val payload = try {
                jwtAccessTokenParser.parse(accessToken)
            } catch (e: Exception) {
                throw InvalidTokenException(e.message?: "Invalid token")
            }
            val userDetails = CustomUserDetails(payload)
            AuthenticationToken.Companion.authenticated(userDetails, null, userDetails.authorities)
        }
    }
}