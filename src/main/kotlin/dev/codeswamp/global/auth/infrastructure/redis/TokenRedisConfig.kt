package dev.codeswamp.global.auth.infrastructure.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.codeswamp.global.auth.domain.model.token.ValidatedRefreshToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class TokenRedisConfig (
    private val redisConnectionFactory: RedisConnectionFactory,
) {

    @Bean
    fun refreshTokenTemplate(): RedisTemplate<String, ValidatedRefreshToken> {
        val template = RedisTemplate<String, ValidatedRefreshToken>()
        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()

        val objectMapper = ObjectMapper().apply {
               registerModule((JavaTimeModule()))
               registerModule(KotlinModule.Builder().build())
        }

        template.valueSerializer = Jackson2JsonRedisSerializer(objectMapper,ValidatedRefreshToken::class.java)

        return template
    }

    @Bean(name = ["temporaryTokenTemplate"])
    fun temporaryTokenTemplate(): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = redisConnectionFactory

        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }
}