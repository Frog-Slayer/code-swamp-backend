package dev.codeswamp.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory() : LettuceConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration().apply {
            hostName= "localhost"
            port = 6379
            database = 0
        }

        return LettuceConnectionFactory(redisConfig)
    }
}