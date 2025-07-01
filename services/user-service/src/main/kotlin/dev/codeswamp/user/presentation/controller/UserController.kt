package dev.codeswamp.user.presentation.controller

import dev.codeswamp.authcommon.security.CustomUserDetails
import dev.codeswamp.user.application.usecase.UserUseCaseFacade
import dev.codeswamp.user.application.usecase.register.RegisterUserCommand
import dev.codeswamp.user.presentation.dto.request.SignUpRequest
import dev.codeswamp.user.presentation.dto.response.SignUpSuccessResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userUseCaseFacade: UserUseCaseFacade,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/signup")
    suspend fun signup(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<SignUpSuccessResponse> {
        val result = userUseCaseFacade.registerUserWithAuthentication(
            RegisterUserCommand(
                token = signUpRequest.token,
                email = signUpRequest.email,
                username = signUpRequest.username,
                nickname = signUpRequest.nickname,
                profileImageUrl = signUpRequest.profileImageUrl
            )
        )

        return ResponseEntity.ok(SignUpSuccessResponse(result.otp))
    }

    @PatchMapping("/nickname")
    suspend fun modifyNickname(@AuthenticationPrincipal userDetails: CustomUserDetails, nickname: String) {
        TODO("not implemented")
    }

    @GetMapping("/nickname/validate")
    fun isValidNickname(nickname: String): Boolean {
        return true
    }

    @PatchMapping("/profile")
    fun modifyProfile(@AuthenticationPrincipal userDetails: CustomUserDetails, profile: String) {
        TODO("not implemented")
    }


}