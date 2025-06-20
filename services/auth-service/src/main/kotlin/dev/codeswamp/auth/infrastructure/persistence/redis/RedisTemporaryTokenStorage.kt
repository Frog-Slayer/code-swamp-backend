package dev.codeswamp.auth.infrastructure.persistence.redis

import dev.codeswamp.auth.application.signup.TemporaryTokenStorage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisTemporaryTokenStorage (
    @Qualifier("temporaryTokenTemplate") private val redisTemplate: RedisTemplate<String, String>
) : TemporaryTokenStorage {
    private val logger =  LoggerFactory.getLogger(javaClass)

    override suspend fun save(token: String, email: String, timeToLiveInMinutes: Long) {
        val key = "tmp_token:$token"
        val duration = Duration.ofMinutes(timeToLiveInMinutes)
        logger.info("save temporary token: $key")

        redisTemplate.opsForValue().set( key, email, duration )
    }

    override suspend fun getAndDelete(token: String): String? {
        val key = "tmp_token:$token"
        logger.info("Retrieving temporary token: $key")
        val value = redisTemplate.opsForValue().getAndDelete(key)
        return value
    }
}