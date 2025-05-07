package dev.codeswamp.global.auth.infrastructure.jwt.service

import dev.codeswamp.global.auth.domain.model.authToken.RawRefreshToken
import dev.codeswamp.global.auth.domain.service.TokenStoreService
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class JwtStore : TokenStoreService{
    override fun storeRefreshToken(token: RawRefreshToken) {
        TODO("Not yet implemented")
    }

    override fun rotateRefreshToken(token: String, newToken: RawRefreshToken) {
        TODO("Not yet implemented")
    }

    override fun findRefreshTokenByUserId(userId: Long): RawRefreshToken? {
        TODO("Not yet implemented")
    }

}