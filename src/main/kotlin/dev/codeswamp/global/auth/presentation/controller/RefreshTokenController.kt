package dev.codeswamp.global.auth.presentation.controller

import dev.codeswamp.global.auth.application.dto.UserProfile
import dev.codeswamp.global.auth.application.service.AuthApplicationService
import dev.codeswamp.global.auth.application.acl.UserProfileFetcher
import dev.codeswamp.global.auth.infrastructure.web.HttpTokenAccessor
import dev.codeswamp.global.auth.presentation.dto.AuthResult
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class RefreshTokenController(
    private val authApplicationService: AuthApplicationService,
    private val userProfileFetcher: UserProfileFetcher,
    private val httpTokenAccessor: HttpTokenAccessor,
){

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) : AuthResult {
        val refreshToken = httpTokenAccessor.extractRefreshToken(request) ?: throw Exception("cannot find refresh token")
        val (newAccessToken, newRefreshToken )= authApplicationService.refresh(refreshToken)

        val userProfile = newAccessToken.authUser.id?.let { userProfileFetcher.fetchUserProfile(it) }
            ?: throw Exception("cannot find user profile")

        httpTokenAccessor.injectRefreshToken(response, newRefreshToken)
        return AuthResult(
            newAccessToken.value,
            userProfile,
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