package dev.codeswamp.core.user.presentation.controller

import dev.codeswamp.core.user.application.acl.AuthServiceAcl
import dev.codeswamp.core.user.application.dto.SignUpCommand
import dev.codeswamp.core.user.application.service.UserApplicationService
import dev.codeswamp.core.user.domain.service.UserService
import dev.codeswamp.core.user.presentation.dto.request.SignUpRequestDto
import jakarta.annotation.PostConstruct
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userApplicationService: UserApplicationService,
    private val authServiceAcl: AuthServiceAcl
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequestDto: SignUpRequestDto) {
        val userId = authServiceAcl.verifyTokenAndCreateAuthUser(signUpRequestDto.token, signUpRequestDto.email)

        userApplicationService.signUp(SignUpCommand(
            userId = userId,
            username = signUpRequestDto.username,
            nickname = signUpRequestDto.nickname,
            profileImageUrl = signUpRequestDto.nickname//TODO (임시)
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