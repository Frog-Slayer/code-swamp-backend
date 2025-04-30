package dev.codeswamp.user.controller

import dev.codeswamp.user.entity.User
import dev.codeswamp.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

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