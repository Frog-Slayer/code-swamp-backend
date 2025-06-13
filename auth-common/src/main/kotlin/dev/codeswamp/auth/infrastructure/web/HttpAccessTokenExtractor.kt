package dev.codeswamp.auth.infrastructure.web

import org.springframework.http.server.reactive.ServerHttpRequest

interface HttpAccessTokenExtractor {
    fun extractAccessTokenFromHeader(request: ServerHttpRequest): String?
}