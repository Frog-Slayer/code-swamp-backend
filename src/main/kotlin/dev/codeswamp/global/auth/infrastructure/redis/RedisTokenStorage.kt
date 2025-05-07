package dev.codeswamp.global.auth.infrastructure.redis

import dev.codeswamp.global.aop.redis.RedisTransaction
import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import dev.codeswamp.global.auth.domain.repository.TokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisTokenStorage(
    private val redisTemplate: RedisTemplate<String, ValidatedRefreshToken>,
    @Value("\${jwt.refresh-token-exp}") private val refreshTokenExpiration : Long
) : TokenRepository {

    @RedisTransaction
    override fun storeRefreshToken(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val uidKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.multi()

        redisTemplate.opsForValue().set(uidKey, refreshToken)
        redisTemplate.opsForValue().set(tokenKey, refreshToken)

        redisTemplate.expire(uidKey,refreshTokenExpiration, TimeUnit.SECONDS    )
        redisTemplate.expire(uidKey,refreshTokenExpiration, TimeUnit.SECONDS    )

        redisTemplate.exec()
    }

    @RedisTransaction
    override fun delete(refreshToken: ValidatedRefreshToken) {
        val uid = refreshToken.authUser.id
        val token = refreshToken.value

        val uidKey = "token:user:$uid"
        val tokenKey = "token:refresh:$token"

        redisTemplate.delete(uidKey)
        redisTemplate.delete(tokenKey)
    }

    @RedisTransaction
    override fun findRefreshTokenByToken(token: String): ValidatedRefreshToken? {
        val tokenKey = "token:refresh:$token"

        return redisTemplate.opsForValue().get(tokenKey)
    }

    @RedisTransaction
    override fun findRefreshTokenByUserId(userId: Long): ValidatedRefreshToken? {
        val uidKey = "token:user:$userId"
        return redisTemplate.opsForValue().get(uidKey)
    }
}