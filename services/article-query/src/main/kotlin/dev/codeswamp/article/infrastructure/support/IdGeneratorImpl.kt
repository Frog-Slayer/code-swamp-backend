package dev.codeswamp.article.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.article.domain.support.IdGenerator
import org.springframework.stereotype.Component

@Component
class IdGeneratorImpl : IdGenerator {
    override fun generateId(): Long = SnowflakeIdGenerator.generateId()
}