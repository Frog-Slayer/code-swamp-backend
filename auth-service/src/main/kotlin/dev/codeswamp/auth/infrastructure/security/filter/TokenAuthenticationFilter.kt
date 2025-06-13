package dev.codeswamp.auth.infrastructure.security.filter

import dev.codeswamp.auth.infrastructure.security.token.AuthenticationToken
import dev.codeswamp.auth.infrastructure.web.HttpTokenAccessor
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

class TokenAuthenticationFilter (
    authenticationManager: ReactiveAuthenticationManager,
    httpTokenAccessor: HttpTokenAccessor
) : AuthenticationWebFilter(authenticationManager) {

    init {
        setServerAuthenticationConverter { exchange ->
            val rawAccessToken = httpTokenAccessor.extractAccessTokenFromHeader(exchange.request)
                ?: return@setServerAuthenticationConverter Mono.empty()

            val preAuthenticated = AuthenticationToken.unauthenticated(rawAccessToken, null)
            Mono.just(preAuthenticated)
        }

        setAuthenticationSuccessHandler { webFilterExchange, authentication ->
            webFilterExchange.chain.filter(webFilterExchange.exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        }
    }

}