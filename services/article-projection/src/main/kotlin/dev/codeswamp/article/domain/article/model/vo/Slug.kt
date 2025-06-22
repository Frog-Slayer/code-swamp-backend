package dev.codeswamp.article.domain.article.model.vo

import dev.codeswamp.article.domain.exception.validation.InvalidFormatException

@JvmInline
value class Slug private constructor(
    val value: String
) {
    companion object {
        private val allowedPattern = Regex("^[a-zA-Z0-9가-힣-_]+$")
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 50

        fun of(value: String?): Slug? {
            if (value.isNullOrBlank()) return null
            val trimmed = value.trim()
            validate(trimmed)
            return Slug(trimmed)
        }

        private fun validate(value: String) {
            if (value.length !in MIN_LENGTH..MAX_LENGTH)
                throw InvalidFormatException("Slug length out of bound: $MIN_LENGTH..$MAX_LENGTH")
            if (!value.matches(allowedPattern)) throw InvalidFormatException("Slug format mismatched")
        }
    }
}