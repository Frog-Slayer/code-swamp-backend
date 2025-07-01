package dev.codeswamp.framework.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.core.domain.IdGenerator

class DefaultIdGenerator : IdGenerator {
    override fun generateId() = SnowflakeIdGenerator.generateId()
}