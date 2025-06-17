package dev.codeswamp.user.presentation.controller

import dev.codeswamp.user.application.acl.AuthAcl
import dev.codeswamp.user.application.dto.SignUpCommand
import dev.codeswamp.user.application.service.UserApplicationService
import dev.codeswamp.user.presentation.dto.request.SignUpRequestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userApplicationService: UserApplicationService,
    private val authAcl: AuthAcl
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signUpRequestDto: SignUpRequestDto) {
        val userId = authAcl.verifyTokenAndCreateAuthUser(signUpRequestDto.token, signUpRequestDto.email)

        userApplicationService.signUp(
            SignUpCommand(
                userId = userId,
                username = signUpRequestDto.username,
                nickname = signUpRequestDto.nickname,
                profileImageUrl = signUpRequestDto.profileImageUrl
            )
        )
    }

    @PatchMapping("/nickname")
    fun modifyNickname(@RequestAttribute userId: Long?, nickname : String) {
    }

    @GetMapping("/nickname/validate")
    fun isValidNickname(nickname: String): Boolean {
        return true
    }

    @PatchMapping("/profile")
    fun modifyProfile(@RequestAttribute userId: Long?, profile: String) {

    }


}