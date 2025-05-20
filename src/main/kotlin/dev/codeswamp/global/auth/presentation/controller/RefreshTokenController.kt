package dev.codeswamp.global.auth.presentation.controller

import dev.codeswamp.global.auth.application.dto.UserProfile
import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class RefreshTokenController(
    private val authApplicationService: AuthApplicationService,
    private val httpTokenAccessor: HttpTokenAccessor,
){

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) : UserProfile {
        val refreshToken = httpTokenAccessor.extractRefreshToken(request) ?: throw Exception("cannot find refresh token")


        TODO("not implemented")
    }
}