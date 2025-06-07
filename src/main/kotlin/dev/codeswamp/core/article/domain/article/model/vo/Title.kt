package dev.codeswamp.core.article.domain.article.model.vo

import dev.codeswamp.core.article.domain.article.exception.InvalidTitleFormatException

@JvmInline
value class Title private constructor(
    val value: String,
) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 255

        fun of(value: String?): Title? {
            if (value.isNullOrEmpty()) return null

            val trimmed = value.trim()
            if (trimmed.length !in MIN_LENGTH..MAX_LENGTH) throw InvalidTitleFormatException(trimmed)

            return Title(trimmed)
        }
    }
}