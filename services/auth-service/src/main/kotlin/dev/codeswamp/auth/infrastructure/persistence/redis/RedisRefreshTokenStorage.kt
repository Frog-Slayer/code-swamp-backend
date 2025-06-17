package dev.codeswamp.auth.infrastructure.persistence.redis

import dev.codeswamp.auth.domain.model.token.ValidatedRefreshToken
import dev.codeswamp.auth.domain.repository.TokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRefreshTokenStorage(
    private val redisTemplate: RedisTemplate<String, ValidatedRefreshToken>,
    @Value("\${jwt.refresh-token-exp}") private val refreshTokenExpiration : Long
) : TokenRepository {

    override fun storeRefreshToken(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val uidKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.opsForValue().set(uidKey, refreshToken)
        redisTemplate.opsForValue().set(tokenKey, refreshToken)

        redisTemplate.expire(uidKey,refreshTokenExpiration, TimeUnit.SECONDS )
        redisTemplate.expire(tokenKey,refreshTokenExpiration, TimeUnit.SECONDS )
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