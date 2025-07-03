package dev.codeswamp.authcommon.security

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import reactor.core.publisher.Mono

class TokenAuthenticationFilter(
    authenticationManager: ReactiveAuthenticationManager,
    authenticationEntryPoint : ServerAuthenticationEntryPoint,
) : AuthenticationWebFilter(authenticationManager) {

    init {
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

        setAuthenticationFailureHandler(
            ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint)
        )
    }

    private fun extractAccessTokenFromHeader(request: ServerHttpRequest): String? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
    }
}