package dev.codeswamp.core.article.infrastructure.cache

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

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