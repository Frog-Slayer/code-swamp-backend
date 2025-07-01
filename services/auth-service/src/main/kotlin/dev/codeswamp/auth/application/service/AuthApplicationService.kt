package dev.codeswamp.auth.application.service

import dev.codeswamp.SnowflakeIdGenerator.generateId
import dev.codeswamp.auth.application.dto.TemporaryLoginResult
import dev.codeswamp.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.auth.application.port.outgoing.UserProfileFetcher
import dev.codeswamp.auth.application.signup.TemporaryTokenService
import dev.codeswamp.auth.domain.model.AuthUser
import dev.codeswamp.auth.domain.model.token.RawAccessToken
import dev.codeswamp.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.auth.domain.model.token.ValidatedAccessToken
import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.repository.AuthUserRepository
import dev.codeswamp.auth.domain.repository.TokenRepository
import dev.codeswamp.auth.domain.service.RefreshTokenRotator
import dev.codeswamp.auth.domain.service.TokenGenerator
import dev.codeswamp.auth.domain.service.TokenParser
import dev.codeswamp.auth.domain.service.TokenValidator
import dev.codeswamp.auth.domain.support.IdGenerator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


data class SignUpResult(
    val userId: Long,
    val otp: String,
)

@Service
class AuthApplicationService(
    private val temporaryTokenService: TemporaryTokenService,
    private val userProfileFetcher: UserProfileFetcher,
    private val authUserRepository: AuthUserRepository,

    private val tokenRepository: TokenRepository,
    private val idGenerator: IdGenerator,
    private val tokenValidator: TokenValidator,
    private val tokenGenerator: TokenGenerator,
    private val tokenRotator: RefreshTokenRotator,
    private val tokenParser: TokenParser,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    suspend fun signup(email: String, signupToken: String): SignUpResult {
        if (!temporaryTokenService.authenticate(signupToken, email))
            throw IllegalStateException("Invalid or expired signup token")

        val newUser = AuthUser.createUser(
            generateId = idGenerator::generateId,
            email = email
        )

        authUserRepository.save(newUser)
        val otp = temporaryTokenService.generateOtp(email)

        logger.info("userId ${newUser.id}, otp ${otp}")

        return SignUpResult(
            userId = newUser.id,
            otp = otp
        )
    }

    suspend fun findByUsername(username: String): AuthUser? {
        return authUserRepository.findByEmail(username)
    }

    suspend fun refresh(rawRefreshToken: RawRefreshToken): ValidatedTokenPair {
        val oldRefreshToken = tokenValidator.validateRefreshToken(rawRefreshToken)
        val authUser = oldRefreshToken.authUser

        val newRefreshToken = issueAndStoreRefreshToken(authUser)
        val newAccessToken = tokenGenerator.generateAccessToken(authUser)

        return ValidatedTokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }

    fun issueAccessToken(authUser: AuthUser): ValidatedAccessToken {
        return tokenGenerator.generateAccessToken(authUser)
    }

    suspend fun issueAndStoreRefreshToken(authUser: AuthUser): ValidatedRefreshToken {
        val refreshToken = tokenGenerator.generateRefreshToken(authUser)
        tokenRotator.rotate(refreshToken)

        return refreshToken
    }

    suspend fun loginWithTemporaryToken(email: String, token: String): TemporaryLoginResult {
        val isTokenValid = temporaryTokenService.authenticate(token, email)

        if (!isTokenValid) throw Exception("token is invalid")

        val authUser = authUserRepository.findByEmail(email) ?: throw Exception("user not found")

        val accessToken = tokenGenerator.generateAccessToken(authUser)
        val refreshToken = issueAndStoreRefreshToken(authUser)

        val userProfile = userProfileFetcher.fetchUserProfile(authUser.id)

        return TemporaryLoginResult(
            ValidatedTokenPair(accessToken, refreshToken),
            userProfile
        )
    }

    @Transactional
    suspend fun deleteRefreshTokenByUserId(userId: Long) {
        tokenRepository.findRefreshTokenByUserId(userId)?.let {
            tokenRepository.delete(it)
        }
    }

    fun validateAccessToken(rawAccessToken: RawAccessToken): ValidatedAccessToken {
        return tokenValidator.validateAccessToken(rawAccessToken)
    }

    fun parseAccessToken(accessToken: String): RawAccessToken {
        return tokenParser.parseAccessToken(accessToken)
    }

    fun parseRefreshToken(refreshToken: String): RawRefreshToken {
        return tokenParser.parseRefreshToken(refreshToken)
    }
}