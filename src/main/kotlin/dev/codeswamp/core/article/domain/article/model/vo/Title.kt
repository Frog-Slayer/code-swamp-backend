package dev.codeswamp.core.article.domain.article.model.vo

@JvmInline
value class Title private constructor(
    val value: String,
) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 256

        fun of(value: String?): Title? {
            if (value.isNullOrEmpty()) return null

            val trimmed = value.trim()
            require(trimmed.length in MIN_LENGTH..MAX_LENGTH) { "Invalid title length" }

            return Title(trimmed)
        }
    }
}