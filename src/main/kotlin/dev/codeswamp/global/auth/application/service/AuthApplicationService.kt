package dev.codeswamp.global.auth.application.service

import dev.codeswamp.global.auth.application.acl.UserProfileFetcher
import dev.codeswamp.global.auth.application.dto.TemporaryLoginResult
import dev.codeswamp.global.auth.application.dto.ValidatedTokenPair
import dev.codeswamp.global.auth.application.signup.TemporaryTokenService
import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.token.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.service.AuthUserService
import dev.codeswamp.global.auth.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class AuthApplicationService (
    private val tokenService: TokenService,
    private val authUserService: AuthUserService,
    private val temporaryTokenService: TemporaryTokenService,
    private val userProfileFetcher: UserProfileFetcher
) : TokenService by tokenService,
    AuthUserService by authUserService
{
    fun refresh(rawRefreshToken: RawRefreshToken) : ValidatedTokenPair {
        val oldRefreshToken = validateRefreshToken(rawRefreshToken)
        val authUser = oldRefreshToken.authUser

        val newRefreshToken = issueAndStoreRefreshToken(authUser)

        val newAccessToken = issueAccessToken(authUser)

        return ValidatedTokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        )
    }

    fun issueAndStoreRefreshToken(authUser: AuthUser) : ValidatedRefreshToken {
        val refreshToken = issueRefreshToken(authUser)
        rotateRefreshToken(refreshToken)

        return refreshToken
    }

    fun loginWithTemporaryToken(email: String, token: String): TemporaryLoginResult {
        val isTokenValid = temporaryTokenService.authenticate(token, email)
        temporaryTokenService.deleteTemporaryToken(token)

        if (!isTokenValid) throw Exception("token is invalid")

        val authUser = findByUsername(email) ?: throw Exception("user not found")

        val accessToken = issueAccessToken(authUser)
        val refreshToken = issueAndStoreRefreshToken(authUser)

        val userProfile = requireNotNull(authUser.id?.let { userProfileFetcher.fetchUserProfile(it) })

        return TemporaryLoginResult(
            ValidatedTokenPair(accessToken, refreshToken),
            userProfile
        )
    }
}