package dev.codeswamp.auth.domain.service

import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.Role
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.repository.AuthUserRepository
import dev.codeswamp.auth.domain.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenValidator(
    private val tokenRepository: TokenRepository,
    private val authUserRepository: AuthUserRepository
){
    fun validateAccessToken(accessToken: RawAccessToken) : ValidatedAccessToken {
        require( !accessToken.expired()) {"Token expired!"}

        val authUser = AuthUser.of(
            id = accessToken.userId,
            email = accessToken.username,
            roles = accessToken.roles.map {
                Role.from(it) ?: throw IllegalArgumentException("Invalid role value!")
            }
        )

        return ValidatedAccessToken(
            value = accessToken.value,
            authUser = authUser,
            expiration = accessToken.expiration
        )
    }

    suspend fun validateRefreshToken(refreshToken: RawRefreshToken) : ValidatedRefreshToken {
        require( !refreshToken.expired()) {"Token expired!"}

        val savedRefreshToken = tokenRepository.findRefreshTokenByToken(refreshToken.value)
            ?: throw IllegalStateException("No user found for token ${refreshToken.value}")//TODO

        val authUserId = savedRefreshToken.authUser.id
        val authUser = authUserRepository.findById(authUserId) ?: throw IllegalStateException("No user found")

        return ValidatedRefreshToken(
            value = refreshToken.value,
            authUser = authUser,
            expiration = refreshToken.expiration
        )

    }
}