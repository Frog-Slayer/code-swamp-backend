package dev.codeswamp.global.auth.infrastructure.oauth2.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginFailureHandler(
    private val objectMapper: ObjectMapper,
) : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"

        val errorResponse = mapOf(
            "error" to "Authentication failed",
            "message" to (exception.message ?: "Unknown error")
        )

        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}