package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.repository.TokenRepository
import dev.codeswamp.global.auth.domain.util.TokenGenerator
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenServiceImpl(
    private val tokenParser: TokenParser,
    private val tokenGenerator: TokenGenerator,
    private val userFinder: UserFinder,
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

        val authUser = userFinder.findBySubject(accessToken.sub)
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

        return ValidatedRefreshToken(
            value = refreshToken.value,
            authUser = savedRefreshToken.authUser,//TODO: 취약점 존재. 리프레시 토큰이 저장된 상태에서 사용자가 사라지는 경우 처리 필요
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
        tokenRepository.findRefreshTokenByUserId(newToken.authUser.id)?.let {
            tokenRepository.delete(it)
        }

        tokenRepository.storeRefreshToken(newToken)
    }

    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        return tokenRepository.findRefreshTokenByUserId(userId)
    }
}