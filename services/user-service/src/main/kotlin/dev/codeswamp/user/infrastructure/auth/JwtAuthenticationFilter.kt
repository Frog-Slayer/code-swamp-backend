package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.token.AccessTokenParser
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.Instant

@Component
class JwtAuthenticationFilter(
    private val tokenParser: AccessTokenParser,
    private val tokenExtractor: HttpAccessTokenExtractor
) : WebFilter {

    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        val httpRequest = exchange.request

        val token = tokenExtractor.extractAccessTokenFromHeader(httpRequest)
        val userId = token
            ?.let { tokenParser.parse(it) }
            ?.takeIf { !Instant.now().isAfter(it.expiration) }
            ?.userId

        if (userId != null) httpRequest.attributes["userId"] = userId

        return chain.filter(exchange)
    }
}