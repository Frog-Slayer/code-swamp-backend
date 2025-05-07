package dev.codeswamp.global.auth.infrastructure.redis

import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.repository.TokenRepository
import org.springframework.stereotype.Repository

@Repository
class RedisTokenStorage : TokenRepository {
    override fun storeRefreshToken(refreshToken: ValidatedRefreshToken) {
        TODO("Not yet implemented")
    }

    override fun delete(refreshToken: ValidatedRefreshToken) {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByToken(token: String): ValidatedRefreshToken? {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        TODO("Not yet implemented")
    }
}