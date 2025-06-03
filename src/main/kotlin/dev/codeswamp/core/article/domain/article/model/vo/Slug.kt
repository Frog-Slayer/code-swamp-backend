package dev.codeswamp.core.article.domain.article.model.vo

data class Slug private constructor(
    val value: String?
) {
    companion object {
        private val allowedPattern = Regex("^[a-zA-Z0-9가-힣-_]+$")
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 50

        fun of(isPublished: Boolean, value: String?): Slug {
            if (isPublished) requireNotNull(value) { "Slug should not be null" }

            if (value != null) {
                val trimmed = value.trim()
                require(trimmed.length in MIN_LENGTH..MAX_LENGTH) { "Invalid slug length" }
                require(trimmed.matches(allowedPattern)) { "Invalid slug format" }

                return Slug(trimmed)
            }

            return Slug(null)
        }
    }
}