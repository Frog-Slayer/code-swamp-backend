package dev.codeswamp.article.domain.folder.model

import dev.codeswamp.article.domain.exception.validation.InvalidFormatException

@JvmInline
value class Name private constructor(
    val value: String
) {
    companion object {
        fun of(name: String): Name {
            val trimmed = name.trim()
            if (trimmed.length !in 1..31) throw InvalidFormatException("Length of Folder name must be in 1 to 31.: $name")
            if (!trimmed.matches(Regex("^[a-zA-Z0-9가-힣 _-]+$"))) throw InvalidFormatException("Folder name contains invalid characters: $name")
            return Name(trimmed)
        }

        fun ofRoot(name: String): Name {
            if (!name.startsWith("@")) throw InvalidFormatException("Root folder name must start with '@'")
            if (name.length !in 2..31) throw InvalidFormatException("Length of Root folder name must be in 2 to 31.")
            if (!name.matches(Regex("^@[a-zA-Z0-9_\\-]+$"))) throw InvalidFormatException("Root folder name contains invalid characters.")
            return Name(name)
        }

        fun from(name: String) = Name(name)
    }


}