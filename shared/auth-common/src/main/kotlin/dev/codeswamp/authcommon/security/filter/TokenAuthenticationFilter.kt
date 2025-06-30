package dev.codeswamp.authcommon.security.filter

import dev.codeswamp.authcommon.security.token.AuthenticationToken
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

class TokenAuthenticationFilter(
    authenticationManager: ReactiveAuthenticationManager,
) : AuthenticationWebFilter(authenticationManager) {

    init {
        setServerAuthenticationConverter { exchange ->
            val rawAccessToken = extractAccessTokenFromHeader(exchange.request)
                ?: return@setServerAuthenticationConverter Mono.empty()

            val preAuthenticated = AuthenticationToken.unauthenticated(rawAccessToken, null)
            Mono.just(preAuthenticated)
        }

        setAuthenticationSuccessHandler { webFilterExchange, authentication ->
            webFilterExchange.chain.filter(webFilterExchange.exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        }
    }

    private fun extractAccessTokenFromHeader(request: ServerHttpRequest): String? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
    }
}