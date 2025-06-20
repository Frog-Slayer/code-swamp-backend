package dev.codeswamp.core.user.presentation.controller

import dev.codeswamp.core.user.application.acl.AuthAcl
import dev.codeswamp.core.user.application.dto.SignUpCommand
import dev.codeswamp.core.user.application.service.UserApplicationService
import dev.codeswamp.core.user.presentation.dto.request.SignUpRequestDto
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userApplicationService: UserApplicationService,
    private val authAcl: AuthAcl
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequestDto: SignUpRequestDto) {
        val userId = authAcl.verifyTokenAndCreateAuthUser(signUpRequestDto.token, signUpRequestDto.email)

        userApplicationService.signUp(SignUpCommand(
            userId = userId,
            username = signUpRequestDto.username,
            nickname = signUpRequestDto.nickname,
            profileImageUrl = signUpRequestDto.profileImageUrl
        ))
    }

    @PatchMapping("/nickname")
    fun modifyNickname(@AuthenticationPrincipal  userDetails: UserDetails, nickname : String) {
    }

    @GetMapping("/nickname/validate")
    fun isValidNickname(nickname: String): Boolean {
        return true
    }

    @PatchMapping("/profile")
    fun modifyProfile(@AuthenticationPrincipal  userDetails: UserDetails, profile: String) {

    }


}