package dev.codeswamp.global.auth.infrastructure.redis

import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.repository.TokenRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisTokenStorage(
    private val redisTemplate: RedisTemplate<String, ValidatedRefreshToken>,
) : TokenRepository {

    override fun storeRefreshToken(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val uidKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.opsForValue().set(uidKey, refreshToken)
        redisTemplate.opsForValue().set(tokenKey, refreshToken)
    }

    override fun delete(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val uidKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.delete(uidKey)
        redisTemplate.delete(tokenKey)
    }

    override fun findRefreshTokenByToken(token: String): ValidatedRefreshToken? {
        val tokenKey = "token:refresh:$token"

        return redisTemplate.opsForValue().get(tokenKey)
    }

    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        val uidKey = "token:user:$userId"
        return redisTemplate.opsForValue().get(uidKey)
    }
}