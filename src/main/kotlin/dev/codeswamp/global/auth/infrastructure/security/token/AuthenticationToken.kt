package dev.codeswamp.global.auth.infrastructure.security.token

import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

sealed class Principal {
    data class AccessToken(val token: RawAccessToken) : Principal()
    data class AuthenticatedUser(val userDetails: UserDetails) : Principal()
}

class AuthenticationToken private constructor(
    private val principal: Principal,
    private val credentials: Any?,
    authorities: Collection<GrantedAuthority?>? = null,
    isAuthenticated: Boolean
): AbstractAuthenticationToken(authorities) {

    init {
        this.isAuthenticated = isAuthenticated
    }

    override fun getCredentials(): Any? {
        return  credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    companion object {
        fun authenticated (
            principal: UserDetails,
            credentials: Any?,
            authorities: Collection<GrantedAuthority?>?
        ) : AuthenticationToken {
            return AuthenticationToken(Principal.AuthenticatedUser(principal),
                credentials, authorities, true)
        }

        fun unAuthenticated (
            principal: RawAccessToken,
            credentials: Any?,
        ) : AuthenticationToken {
            return AuthenticationToken(Principal.AccessToken(principal), credentials, null, false)
        }
    }

}