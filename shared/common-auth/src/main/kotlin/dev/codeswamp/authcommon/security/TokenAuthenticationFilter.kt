package dev.codeswamp.authcommon.security

import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

class TokenAuthenticationFilter(
    authenticationManager: TokenAuthenticationManager,
) : AuthenticationWebFilter(authenticationManager) {
    private val logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)

    init {
        logger.info("TokenAuthenticationFilter created with manager: $authenticationManager")
        setServerAuthenticationConverter { exchange ->
            val rawAccessToken = extractAccessTokenFromHeader(exchange.request)
                ?: return@setServerAuthenticationConverter Mono.empty()

            val preAuthenticated = AuthenticationToken.Companion.unauthenticated(rawAccessToken, null)
            Mono.just(preAuthenticated)
        }

        setAuthenticationSuccessHandler { webFilterExchange, authentication ->
            webFilterExchange.chain.filter(webFilterExchange.exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        }

        setAuthenticationFailureHandler { webFilterExchange, exception ->
            webFilterExchange.chain.filter(webFilterExchange.exchange)
        }

    }

    private fun extractAccessTokenFromHeader(request: ServerHttpRequest): String? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
    }
}