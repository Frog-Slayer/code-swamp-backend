package dev.codeswamp.auth.infrastructure.persistence.redis

import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.repository.TokenRepository
import dev.codeswamp.auth.infrastructure.persistence.redis.dto.RedisValidatedRefreshToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRefreshTokenStorage(
    private val redisTemplate: RedisTemplate<String, RedisValidatedRefreshToken>,
    @Value("\${jwt.refresh-token-exp}") private val refreshTokenExpiration : Long
) : TokenRepository {

    override suspend fun storeRefreshToken(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val userIdKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.opsForValue().set(userIdKey,
                RedisValidatedRefreshToken.from(refreshToken)
        )

        redisTemplate.opsForValue().set(tokenKey,
            RedisValidatedRefreshToken.from(refreshToken)
        )

        redisTemplate.expire(userIdKey,refreshTokenExpiration, TimeUnit.SECONDS )
        redisTemplate.expire(tokenKey,refreshTokenExpiration, TimeUnit.SECONDS )
    }

    override suspend fun delete(refreshToken: ValidatedRefreshToken) {
        val userId = refreshToken.authUser.id
        val token = refreshToken.value

        val userIdKey = "token:user:$userId"
        val tokenKey = "token:refresh:$token"

        redisTemplate.delete(userIdKey)
        redisTemplate.delete(tokenKey)
    }

    override suspend fun findRefreshTokenByToken(token: String): ValidatedRefreshToken? {
        val tokenKey = "token:refresh:$token"

        return redisTemplate.opsForValue().get(tokenKey)?.toDomain()
    }

    override suspend fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        val userIdKey = "token:user:$userId"
        return redisTemplate.opsForValue().get(userIdKey)?.toDomain()
    }
}