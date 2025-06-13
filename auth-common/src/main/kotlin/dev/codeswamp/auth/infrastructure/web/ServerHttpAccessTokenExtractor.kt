package dev.codeswamp.auth.infrastructure.web

import org.springframework.http.server.reactive.ServerHttpRequest

class ServerHttpAccessTokenExtractor : HttpAccessTokenExtractor {
    override fun extractAccessTokenFromHeader(request: ServerHttpRequest): String? {
        return request.headers.getFirst("Authorization")
            ?.removePrefix("Bearer ")
    }
}