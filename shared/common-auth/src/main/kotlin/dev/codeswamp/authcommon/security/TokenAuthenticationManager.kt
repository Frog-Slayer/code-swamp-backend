package dev.codeswamp.authcommon.security

import dev.codeswamp.authcommon.exception.InvalidTokenException
import dev.codeswamp.authcommon.token.JwtAccessTokenParser
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import reactor.core.publisher.Mono

class TokenAuthenticationManager(
    private val jwtAccessTokenParser: JwtAccessTokenParser,
) : ReactiveAuthenticationManager{
    private val logger = LoggerFactory.getLogger(TokenAuthenticationManager::class.java)

    init {
        logger.info("TokenAuthenticationManager initialized")
    }

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val anonymousUser = CustomUserDetails(null)
        val accessToken = authentication?.principal as? String ?:
        return  Mono.just( AuthenticationToken.Companion.authenticated( anonymousUser, null,anonymousUser.authorities))

        return try {
            val payload = jwtAccessTokenParser.parse(accessToken)
            val userDetails = CustomUserDetails(payload)

            logger.info("TokenManager successfully authenticated")

            Mono.just( AuthenticationToken.Companion.authenticated(userDetails, null, userDetails.authorities))
        } catch (e: Exception) {
            logger.info("TokenManager authenticated as anonymous")

            Mono.just( AuthenticationToken.Companion.authenticated( anonymousUser, null,anonymousUser.authorities))
        }
    }
}