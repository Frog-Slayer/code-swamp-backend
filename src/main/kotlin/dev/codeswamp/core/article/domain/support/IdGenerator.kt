package dev.codeswamp.core.article.domain.support

import dev.codeswamp.core.article.infrastructure.support.SnowflakeIdGenerator

interface IdGenerator {
    companion object {
        fun generate() : Long {
            return SnowflakeIdGenerator.generateId()
        }
   }

    fun generateId() : Long
}