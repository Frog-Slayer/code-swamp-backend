package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.token.AccessTokenParser
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

@Component
class HttpAccessTokenExtractor(
    private val tokenParser: AccessTokenParser,
) {

    fun extractAccessTokenFromHeader(request: ServerHttpRequest): String? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
    }
}