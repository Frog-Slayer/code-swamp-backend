package dev.codeswamp.user.infrastructure.auth

import dev.codeswamp.authcommon.token.AccessTokenParser
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtAuthenticationFilter(
    private val tokenParser: AccessTokenParser,
    private val tokenExtractor: HttpAccessTokenExtractor
) : Filter{
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain?
    ) {
        val httpRequest = request as HttpServletRequest

        val token = tokenExtractor.extractAccessTokenFromHeader(httpRequest)
        val userId = token
            ?.let { tokenParser.parse(it)}
            ?.takeIf { !Instant.now().isAfter(it.expiration)}
            ?.userId

        httpRequest.setAttribute("userId", userId)

        chain?.doFilter(request, response)
    }

}