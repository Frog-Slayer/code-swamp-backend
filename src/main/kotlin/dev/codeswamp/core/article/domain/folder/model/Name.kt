package dev.codeswamp.core.article.domain.folder.model

@JvmInline
value class Name private constructor(
    val value : String
) {
    companion object {
        fun of(name: String) : Name {
            require(name.length in 1..31) { "Folder name must be between 1 and 31 characters." }
            require(name.matches(Regex("^[a-zA-Z0-9가-힣_\\- ]+$"))) { "Folder name contains invalid characters." }
            return Name(name)
        }

        fun ofRoot(name: String) : Name {
            require(name.startsWith("@")) { "Root folder name must start with '@'" }
            require(name.length in 2..30) { "Root folder name must be between 2 and 30 characters." }
            require(name.matches(Regex("^@[a-zA-Z0-9가-힣_\\-]+$"))) { "Root folder name contains invalid characters." }
            return Name(name)
        }
    }


}