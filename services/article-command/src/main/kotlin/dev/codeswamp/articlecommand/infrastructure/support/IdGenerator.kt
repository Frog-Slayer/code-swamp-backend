package dev.codeswamp.articlecommand.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.articlecommand.domain.support.IdGenerator
import org.springframework.stereotype.Component

@Component
class IdGeneratorImpl : IdGenerator {
    override fun generateId() = SnowflakeIdGenerator.generateId()
}