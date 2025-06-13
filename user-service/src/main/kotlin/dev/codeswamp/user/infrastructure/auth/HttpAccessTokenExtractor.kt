package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.token.AccessTokenParser
import dev.codeswamp.authcommon.token.AccessTokenPayload
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class HttpAccessTokenExtractor(
    private val tokenParser : AccessTokenParser,
) {

    fun extractAccessTokenFromHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
            ?.removePrefix("Bearer ")
    }
}