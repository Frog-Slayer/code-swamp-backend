package dev.codeswamp.auth.infrastructure.security.handler

import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.infrastructure.security.user.CustomUserDetails
import dev.codeswamp.auth.infrastructure.web.HttpTokenAccessor
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomLogoutHandler(
    private val httpTokenAccessor: HttpTokenAccessor,
    private val authApplicationService: AuthApplicationService
) : ServerLogoutHandler {

    override fun logout(
        exchange: WebFilterExchange,
        authentication: Authentication?
    ): Mono<Void> {
        httpTokenAccessor.invalidateTokenPair(exchange.exchange.response)

        val userDetails = authentication?.principal as? CustomUserDetails
        val authUserId = userDetails?.getId()

        return if (authUserId != null) {
            mono {
                authApplicationService.deleteRefreshTokenByUserId(authUserId)
                null
            }
        } else Mono.empty()

    }
}