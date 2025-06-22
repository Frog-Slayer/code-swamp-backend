package dev.codeswamp.auth.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.auth.domain.support.IdGenerator
import org.springframework.stereotype.Component

@Component
class IdGeneratorImpl : IdGenerator {
    override fun generateId(): Long {
        return SnowflakeIdGenerator.generateId()
    }
}