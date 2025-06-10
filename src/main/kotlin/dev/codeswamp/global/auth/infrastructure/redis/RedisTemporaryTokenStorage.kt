package dev.codeswamp.global.auth.infrastructure.redis

import dev.codeswamp.global.auth.application.signup.TemporaryTokenStorage
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisTemporaryTokenStorage (
    @Qualifier("temporaryTokenTemplate") private val redisTemplate: RedisTemplate<String, String>
) : TemporaryTokenStorage {
    override fun save(token: String, email: String, timeToLiveInMinutes: Long) {
        val key = "tmp_token:$token"
        val duration = Duration.ofMinutes(timeToLiveInMinutes)
        redisTemplate.opsForValue().set( key, email, duration )
    }

    override fun get(token: String): String? {
        val key = "tmp_token:$token"
        return  redisTemplate.opsForValue().get(key)
    }

    override fun delete(token: String) {
        val key = "tmp_token:$token"
        redisTemplate.delete(key)
    }
}