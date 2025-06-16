package dev.codeswamp.global.auth.infrastructure.security.handler

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component

@Component
class CustomLogoutHandler(
    private val httpTokenAccessor: HttpTokenAccessor,
    private val authApplicationService: AuthApplicationService
) : LogoutHandler {

    override fun logout (
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        httpTokenAccessor.invalidateTokenPair(response)
        val userDetails = authentication?.principal as? CustomUserDetails

        userDetails?.let {
            val authUserId = it.getId()

            if (authUserId != null) {
                authApplicationService.deleteRefreshTokenByUserId(authUserId)
            }
        }
    }
}