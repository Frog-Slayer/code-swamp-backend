package dev.codeswamp.framework.infrastructure.support

import dev.codeswamp.core.application.SystemIdGenerator

class DefaultSystemIdGenerator : SystemIdGenerator {
    override fun generateId() = SnowflakeIdGenerator.generateId()
}