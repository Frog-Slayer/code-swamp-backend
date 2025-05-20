package dev.codeswamp.global.auth.domain.service.impl

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.RawAccessToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.repository.TokenRepository
import dev.codeswamp.global.auth.domain.service.AuthUserService
import dev.codeswamp.global.auth.domain.service.TokenService
import dev.codeswamp.global.auth.domain.util.TokenGenerator
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class TokenServiceImpl(
    private val authUserService: AuthUserService,
    private val tokenParser: TokenParser,
    private val tokenGenerator: TokenGenerator,
    private val tokenRepository: TokenRepository
) : TokenService {

    override fun issueAccessToken(authUser: AuthUser): ValidatedAccessToken {
        return tokenGenerator.generateAccessToken(authUser)
    }

    override fun issueRefreshToken(authUser: AuthUser): ValidatedRefreshToken {
        return tokenGenerator.generateRefreshToken(authUser)
    }

    override fun validateAccessToken(accessToken: RawAccessToken): ValidatedAccessToken {
        require( !accessToken.expired()) {"Token expired!"}

        val authUser = authUserService.findBySubject(accessToken.sub)
            ?: throw IllegalStateException("No user found for token sub")//TODO

        return ValidatedAccessToken(
            value = accessToken.value,
            authUser = authUser,
            expiration = accessToken.expiration
        )
    }

    override fun validateRefreshToken(refreshToken: RawRefreshToken): ValidatedRefreshToken {
        require( !refreshToken.expired()) {"Token expired!"}

        val savedRefreshToken = tokenRepository.findRefreshTokenByToken(refreshToken.value)
            ?: throw IllegalStateException("No user found for token")//TODO

        val authUserId = savedRefreshToken.authUser.id ?: throw IllegalStateException("No user found")
        val authUser = authUserService.findById(authUserId) ?: throw IllegalStateException("No user found")

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

    override fun storeRefreshToken(token: ValidatedRefreshToken) {
        tokenRepository.storeRefreshToken(token)
    }

    override fun rotateRefreshToken(newToken: ValidatedRefreshToken)
    {
        tokenRepository.findRefreshTokenByUserId(
            newToken.authUser.id ?: throw IllegalStateException("not a valid token"))
            ?.let { tokenRepository.delete(it) }

        tokenRepository.storeRefreshToken(newToken)
    }

    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        return tokenRepository.findRefreshTokenByUserId(userId)
    }
}