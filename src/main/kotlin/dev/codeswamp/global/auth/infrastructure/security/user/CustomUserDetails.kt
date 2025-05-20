package dev.codeswamp.global.auth.infrastructure.security.user

import dev.codeswamp.global.auth.domain.model.AuthUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails (
    private val user : AuthUser?
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return user?.role?.let { listOf(SimpleGrantedAuthority(it.toString())) }
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return user?.username
    }
}