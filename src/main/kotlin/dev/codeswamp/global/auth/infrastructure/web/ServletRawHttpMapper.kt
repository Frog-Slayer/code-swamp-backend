package dev.codeswamp.global.auth.infrastructure.web

import dev.codeswamp.global.auth.application.dto.rawHttp.RawHttpRequest
import dev.codeswamp.global.auth.application.dto.RawHttpResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

object ServletRawHttpMapper {
    fun toRawRequest(servletRequest: HttpServletRequest): RawHttpRequest {
        return RawHttpRequest(
            headers = extractHeaders(servletRequest),
            cookies = extractCookies(servletRequest),
        )
    }

    private fun extractHeaders(request: HttpServletRequest): Map<String, String> {
        return request.headerNames.asSequence()
            .associateWith { request.getHeader(it) }
    }

    private fun extractCookies(request: HttpServletRequest): Map<String, String> {
        return request.cookies?.associate { it.name to it.value } ?: emptyMap()
    }

    fun applyToServletResponse(rawResponse: RawHttpResponse, servletResponse: HttpServletResponse) {
        rawResponse.cookies.forEach { (name, value) ->
            val cookie = Cookie(name, value)
            servletResponse.addCookie(cookie)
        }
    }
}