package dev.codeswamp.authgateway.application.service

import dev.codeswamp.authgateway.application.acl.UserProfileFetcher
import dev.codeswamp.authgateway.application.dto.TemporaryLoginResult
import dev.codeswamp.authgateway.application.dto.ValidatedTokenPair
import dev.codeswamp.authgateway.application.signup.TemporaryTokenService
import dev.codeswamp.authgateway.domain.model.AuthUser
import dev.codeswamp.authgateway.domain.model.token.RawRefreshToken
import dev.codeswamp.authgateway.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.authgateway.domain.repository.AuthUserRepository
import dev.codeswamp.authgateway.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class AuthApplicationService (
    private val tokenService: TokenService,
    private val temporaryTokenService: TemporaryTokenService,
    private val userProfileFetcher: UserProfileFetcher,
    private val authUserRepository: AuthUserRepository
) : TokenService by tokenService {

    suspend fun  findByUsername(username: String): AuthUser? {
        return authUserRepository.findByUsername(username)
    }

    suspend fun refresh(rawRefreshToken: RawRefreshToken) : ValidatedTokenPair {
        val oldRefreshToken = validateRefreshToken(rawRefreshToken)
        val authUser = oldRefreshToken.authUser

        val newRefreshToken = issueAndStoreRefreshToken(authUser)

        val newAccessToken = issueAccessToken(authUser)

        return ValidatedTokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }

    suspend fun issueAndStoreRefreshToken(authUser: AuthUser) : ValidatedRefreshToken {
        val refreshToken = issueRefreshToken(authUser)
        rotateRefreshToken(refreshToken)

        return refreshToken
    }

    suspend fun loginWithTemporaryToken(email: String, token: String): TemporaryLoginResult {
        val isTokenValid = temporaryTokenService.authenticate(token, email)
        temporaryTokenService.deleteTemporaryToken(token)

        if (!isTokenValid) throw Exception("token is invalid")

        val authUser = authUserRepository.findByUsername(email) ?: throw Exception("user not found")

        val accessToken = issueAccessToken(authUser)
        val refreshToken = issueAndStoreRefreshToken(authUser)

        val userProfile = requireNotNull(authUser.id?.let { userProfileFetcher.fetchUserProfile(it) })

        return TemporaryLoginResult(
            ValidatedTokenPair(accessToken, refreshToken),
            userProfile
        )
    }
}