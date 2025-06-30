package dev.codeswamp.authcommon.security.user

import dev.codeswamp.authcommon.token.AccessTokenPayload
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val payload: AccessTokenPayload?
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return payload?.roles?.let { listOf(SimpleGrantedAuthority(it.toString())) }
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return payload?.sub
    }

    fun getId(): Long? {
        return payload?.userId
    }
}