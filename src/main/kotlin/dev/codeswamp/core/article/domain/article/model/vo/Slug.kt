package dev.codeswamp.core.article.domain.article.model.vo

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
            return Slug( trimmed)
        }

        private fun validate(value: String) {
            require(value.length in MIN_LENGTH..MAX_LENGTH) { "Invalid slug length" }
            require(value.matches(allowedPattern)) { "Invalid slug format" }
        }
    }
}