package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.AccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RefreshToken
import org.springframework.stereotype.Service

@Service
class TokenService : TokenIssueService, TokenStoreService, TokenValidationService {
    override fun issueAccessToken(authUser: AuthUser): AccessToken {
        TODO("Not yet implemented")
    }

    override fun issueRefreshToken(authUser: AuthUser): RefreshToken {
        TODO("Not yet implemented")
    }

    override fun storeRefreshToken(token: RefreshToken) {
        TODO("Not yet implemented")
    }

    override fun rotateRefreshToken(token: String, newToken: RefreshToken) {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByUserId(userId: Long): RefreshToken? {
        TODO("Not yet implemented")
    }

    override fun validateAccessToken(accessToken: AccessToken) {
        TODO("Not yet implemented")
    }

    override fun validateRefreshToken(refreshToken: RefreshToken) {
        TODO("Not yet implemented")
    }
}