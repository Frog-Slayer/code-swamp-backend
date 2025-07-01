package dev.codeswamp.projection.infrastructure.support

import dev.codeswamp.SnowflakeIdGenerator
import dev.codeswamp.core.application.SystemIdGenerator
import org.springframework.stereotype.Component

@Component
class SystemIdGeneratorImpl : SystemIdGenerator {
    override fun generateId() = SnowflakeIdGenerator.generateId()
}