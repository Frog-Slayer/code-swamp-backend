package dev.codeswamp.core.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.core.application.SystemIdGenerator

class DefaultSystemIdGenerator : SystemIdGenerator {
    override fun generateId() = SnowflakeIdGenerator.generateId()
}