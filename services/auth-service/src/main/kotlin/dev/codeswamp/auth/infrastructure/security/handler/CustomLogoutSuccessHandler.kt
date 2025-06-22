package dev.codeswamp.auth.infrastructure.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomLogoutSuccessHandler(
    private val objectMapper: ObjectMapper,
) : ServerLogoutSuccessHandler {

    override fun onLogoutSuccess(
        exchange: WebFilterExchange,
        authentication: Authentication?
    ): Mono<Void> {
        val response = exchange.exchange.response
        response.statusCode = HttpStatus.OK
        response.headers.contentType = APPLICATION_JSON

        val message = mapOf("message" to "logout success")

        return response.writeWith(
            Mono.fromSupplier {
                val messageAsJson = objectMapper.writeValueAsBytes(message)
                response.bufferFactory().wrap(messageAsJson)
            }
        )
    }
}