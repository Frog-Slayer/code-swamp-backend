package dev.codeswamp.authcommon.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

sealed class Principal {
    data class AccessToken(val token: String) : Principal()
    data class AuthenticatedUser(val userDetails: CustomUserDetails) : Principal()
}

class AuthenticationToken private constructor(
    private val principal: Principal,
    private val credentials: Any?,
    authorities: Collection<GrantedAuthority?>? = null,
    isAuthenticated: Boolean
) : AbstractAuthenticationToken(authorities) {

    init {
        this.isAuthenticated = isAuthenticated
    }

    override fun getCredentials(): Any? {
        return credentials
    }

    override fun getPrincipal(): Any? {
        return when (principal) {
            is Principal.AuthenticatedUser -> principal.userDetails
            is Principal.AccessToken -> principal.token
        }
    }

    companion object {
        fun authenticated(
            principal: CustomUserDetails,
            credentials: Any?,
            authorities: Collection<GrantedAuthority?>?
        ): AuthenticationToken {
            return AuthenticationToken(
                Principal.AuthenticatedUser(principal),
                credentials, authorities, true
            )
        }

        fun unauthenticated(
            principal: String,
            credentials: Any?,
        ): AuthenticationToken {
            return AuthenticationToken(Principal.AccessToken(principal), credentials, null, false)
        }
    }

}