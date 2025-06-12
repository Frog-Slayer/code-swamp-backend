package dev.codeswamp.core.article.infrastructure.cache

import dev.codeswamp.global.auth.application.signup.TemporaryTokenStorage
import org.hibernate.annotations.Cache
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Repository
import java.time.Duration

@Configuration
class FolderDeletionRedisTemplate(
    private val redisConnectionFactory: RedisConnectionFactory,
){
    @Bean(name = ["folderDeletionCache"])
    fun temporaryTokenTemplate(): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = redisConnectionFactory

        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }
}