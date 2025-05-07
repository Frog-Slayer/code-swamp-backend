package dev.codeswamp.global.auth.infrastructure.redis

import dev.codeswamp.global.auth.domain.model.authToken.ValidatedRefreshToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RefreshTokenRedisConfig {

    @Bean
    fun refreshTokenRedisConnectionFactory() : LettuceConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration().apply {
            hostName= "localhost"
            port = 6379
            database = 0
        }

        return LettuceConnectionFactory(redisConfig)
    }

    @Bean
    fun refreshTokenTemplate(): RedisTemplate<String, ValidatedRefreshToken> {
        val template = RedisTemplate<String, ValidatedRefreshToken>()
        template.connectionFactory = refreshTokenRedisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()

        return template
    }

}