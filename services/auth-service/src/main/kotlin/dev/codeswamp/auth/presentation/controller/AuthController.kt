package dev.codeswamp.auth.presentation.controller

import dev.codeswamp.auth.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.auth.application.service.AuthApplicationService
import dev.codeswamp.auth.infrastructure.web.HttpTokenAccessor
import dev.codeswamp.auth.presentation.dto.AuthResult
import dev.codeswamp.auth.presentation.dto.TemporaryLoginRequest
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class AuthController(
    private val authApplicationService: AuthApplicationService,
    private val userProfileFetcher: UserProfileFetcher,
    private val httpTokenAccessor: HttpTokenAccessor,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/refresh")
    suspend fun refreshToken(request: ServerHttpRequest, response: ServerHttpResponse): AuthResult {
        val refreshToken = httpTokenAccessor.extractRefreshToken(request) ?: throw Exception("cannot find refresh token")
        val validatedTokenPair = authApplicationService.refresh(refreshToken)
        val (newAccessToken, newRefreshToken) = validatedTokenPair

        val userProfile = newAccessToken.authUser.id?.let { userProfileFetcher.fetchUserProfile(it) }
            ?: throw Exception("cannot find user profile")

        httpTokenAccessor.injectTokenPair(response, validatedTokenPair)

        return AuthResult(
            newAccessToken.value,
            userProfile,
        )
    }

    @PostMapping("/temp-login")
    suspend fun temporaryLogin(response: ServerHttpResponse, @RequestBody tempLoginRequest: TemporaryLoginRequest): AuthResult {
        val email = tempLoginRequest.email
        val token = tempLoginRequest.token

        val (tokenPair, userProfile) = authApplicationService.loginWithTemporaryToken(email, token)
        httpTokenAccessor.injectTokenPair(response, tokenPair)

        logger.info("temp-login success")

        return AuthResult(
            tokenPair.accessToken.value,
            userProfile
        )
    }
}