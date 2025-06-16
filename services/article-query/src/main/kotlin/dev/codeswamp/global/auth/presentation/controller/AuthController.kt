package dev.codeswamp.global.auth.presentation.controller

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.application.acl.UserProfileFetcher
import dev.codeswamp.global.auth.application.signup.TemporaryTokenService
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import dev.codeswamp.global.auth.presentation.dto.AuthResult
import dev.codeswamp.global.auth.presentation.dto.TemporaryLoginRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authApplicationService: AuthApplicationService,
    private val temporaryTokenService: TemporaryTokenService,
    private val userProfileFetcher: UserProfileFetcher,
    private val httpTokenAccessor: HttpTokenAccessor,
){
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) : AuthResult {
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
    fun temporaryLogin(response: HttpServletResponse, @RequestBody tempLoginRequest: TemporaryLoginRequest) : AuthResult {
        val email = tempLoginRequest.email
        val token = tempLoginRequest.token

        val (tokenPair, userProfile)= authApplicationService.loginWithTemporaryToken(email, token)
        httpTokenAccessor.injectTokenPair(response, tokenPair)

        logger.info("temp-login success")

        return AuthResult(
            tokenPair.accessToken.value,
            userProfile
        )
    }

    @GetMapping("/test")
    fun test() : TestJSON {
        return TestJSON()
    }
}

data class TestJSON (
    val testVal : String = "success"
)