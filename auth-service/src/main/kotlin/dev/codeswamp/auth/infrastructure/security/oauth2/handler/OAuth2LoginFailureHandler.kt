package dev.codeswamp.auth.infrastructure.security.oauth2.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class OAuth2LoginFailureHandler(
    private val objectMapper: ObjectMapper,
) : ServerAuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        exchange: WebFilterExchange,
        exception: AuthenticationException?
    ): Mono<Void> {
        val response = exchange.exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers.contentType = APPLICATION_JSON

        val errorResponse = mapOf(
            "error" to "Authentication failed",
            "message" to (exception?.message ?: "Unknown error")
        )

        return response.writeWith(
            Mono.fromSupplier {
                val errorResponseAsJson = objectMapper.writeValueAsBytes(errorResponse)
                response.bufferFactory().wrap(errorResponseAsJson)
            }
        )
    }
}