package dev.codeswamp.global.auth.infrastructure

import dev.codeswamp.global.auth.application.service.AuthApplicationService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class CustomUserDetailsService(
    private val authApplicationService: AuthApplicationService,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val authUser = authApplicationService.findUserByUsername(username)

    }
}