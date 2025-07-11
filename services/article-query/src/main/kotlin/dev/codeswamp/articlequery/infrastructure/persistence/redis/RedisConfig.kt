package dev.codeswamp.articlequery.infrastructure.persistence.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    private val redisConnectionFactory: ReactiveRedisConnectionFactory,
) {

    @Bean(name = ["viewCountRedisTemplate"])
    fun viewCountRedisTemplate(): ReactiveRedisTemplate<String, String> {
        val keySerializer = StringRedisSerializer()

        val objectMapper = ObjectMapper().apply {
            registerModule((JavaTimeModule()))
            registerModule(KotlinModule.Builder().build())
        }

        val valueSerializer = Jackson2JsonRedisSerializer(objectMapper, String::class.java)

        val serializationContext = RedisSerializationContext
            .newSerializationContext<String, String>(keySerializer)
            .value(valueSerializer)
            .build()

        return ReactiveRedisTemplate(redisConnectionFactory, serializationContext)
    }
}