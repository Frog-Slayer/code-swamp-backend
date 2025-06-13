package dev.codeswamp.authgateway.domain.service.impl

import dev.codeswamp.authgateway.domain.model.AuthUser
import dev.codeswamp.authgateway.domain.model.token.RawAccessToken
import dev.codeswamp.authgateway.domain.model.token.RawRefreshToken
import dev.codeswamp.authgateway.domain.model.token.SubjectType
import dev.codeswamp.authgateway.domain.model.token.ValidatedAccessToken
import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.authgateway.domain.repository.AuthUserRepository
import dev.codeswamp.authgateway.domain.repository.TokenRepository
import dev.codeswamp.authgateway.domain.service.TokenService
import dev.codeswamp.authgateway.domain.util.TokenGenerator
import dev.codeswamp.authgateway.domain.util.TokenParser
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class TokenServiceImpl(
    private val tokenParser: TokenParser,
    private val tokenGenerator: TokenGenerator,
    private val authUserRepository: AuthUserRepository,
    private val tokenRepository: TokenRepository
) : TokenService {

    override fun issueAccessToken(authUser: AuthUser): ValidatedAccessToken {
        return tokenGenerator.generateAccessToken(authUser)
    }

    override fun issueRefreshToken(authUser: AuthUser): ValidatedRefreshToken {
        return tokenGenerator.generateRefreshToken(authUser)
    }

    override suspend fun validateAccessToken(accessToken: RawAccessToken): ValidatedAccessToken {
        require( !accessToken.expired()) {"Token expired!"}

        val authUser = authUserRepository.let {
            val subject = accessToken.sub
            when (subject.type) {
                SubjectType.USERNAME -> it.findByUsername(subject.value)
            }
            ?: throw IllegalStateException("No user found for token sub")//TODO
        }

        return ValidatedAccessToken(
            value = accessToken.value,
            authUser = authUser,
            expiration = accessToken.expiration
        )
    }

    override suspend fun validateRefreshToken(refreshToken: RawRefreshToken): ValidatedRefreshToken {
        require( !refreshToken.expired()) {"Token expired!"}

        val savedRefreshToken = tokenRepository.findRefreshTokenByToken(refreshToken.value)
            ?: throw IllegalStateException("No user found for token ${refreshToken.value}")//TODO

        val authUserId = savedRefreshToken.authUser.id ?: throw IllegalStateException("No user found")
        val authUser = authUserRepository.findById(authUserId) ?: throw IllegalStateException("No user found")

        return ValidatedRefreshToken(
            value = refreshToken.value,
            authUser = authUser,
            expiration = refreshToken.expiration
        )
    }

    override fun parseAccessToken(accessToken: String): RawAccessToken {
        return tokenParser.parseAccessToken(accessToken)
    }

    override fun parseRefreshToken(refreshToken: String): RawRefreshToken {
        return tokenParser.parseRefreshToken(refreshToken)
    }

    override suspend fun storeRefreshToken(token: ValidatedRefreshToken) {
        tokenRepository.storeRefreshToken(token)
    }

    override suspend fun rotateRefreshToken(newToken: ValidatedRefreshToken)
    {
        tokenRepository.findRefreshTokenByUserId(
            newToken.authUser.id ?: throw IllegalStateException("not a valid token"))
            ?.let { tokenRepository.delete(it) }

        tokenRepository.storeRefreshToken(newToken)
    }

    override suspend fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        return tokenRepository.findRefreshTokenByUserId(userId)
    }

    override suspend fun deleteRefreshTokenByUserId(userId: Long) {
        tokenRepository.findRefreshTokenByUserId(userId)?.let { tokenRepository.delete(it) }
    }
}