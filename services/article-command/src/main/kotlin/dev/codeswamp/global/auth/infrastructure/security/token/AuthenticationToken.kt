package dev.codeswamp.global.auth.infrastructure.security.token

import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.infrastructure.security.user.CustomUserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

sealed class Principal {
    data class AccessToken(val token: RawAccessToken) : Principal()
    data class AuthenticatedUser(val userDetails: CustomUserDetails) : Principal()
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
        return when (principal) {
            is Principal.AuthenticatedUser -> principal.userDetails
            is Principal.AccessToken -> principal.token
        }
    }

    companion object {
        fun authenticated (
            principal: CustomUserDetails,
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