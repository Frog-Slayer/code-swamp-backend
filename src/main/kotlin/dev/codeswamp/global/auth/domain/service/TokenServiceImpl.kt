package dev.codeswamp.global.auth.domain.service

import dev.codeswamp.global.auth.domain.model.AuthUser
import dev.codeswamp.global.auth.domain.model.authToken.RawAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedAccessToken
import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.util.TokenGenerator
import dev.codeswamp.global.auth.domain.util.TokenParser
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val tokenParser: TokenParser,
    private val tokenGenerator: TokenGenerator
) : TokenService {

    fun refreshAccessToken(refreshToken: ValidatedRefreshToken): ValidatedAccessToken {
        TODO("not yet implemented")
    }

    override fun issueAccessToken(authUser: AuthUser): ValidatedAccessToken {
        TODO("Not yet implemented")
    }

    override fun issueRefreshToken(authUser: AuthUser): ValidatedRefreshToken {
        TODO("Not yet implemented")
    }

    override fun validateAccessToken(accessToken: RawAccessToken): ValidatedAccessToken {
        TODO("Not yet implemented")
    }

    override fun validateRefreshToken(refreshToken: RawRefreshToken): ValidatedRefreshToken {
        TODO("Not yet implemented")
    }

    override fun storeRefreshToken(token: ValidatedRefreshToken) {
        TODO("Not yet implemented")
    }

    override fun rotateRefreshToken(
        token: String,
        newToken: ValidatedRefreshToken
    ) {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        TODO("Not yet implemented")
    }
}