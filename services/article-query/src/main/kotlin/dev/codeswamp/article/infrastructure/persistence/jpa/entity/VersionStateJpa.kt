package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.article.domain.article.model.VersionState

enum class VersionStateJpa {
    ARCHIVED,
    DRAFT,
    PUBLISHED;

    fun toDomain(): VersionState = VersionState.valueOf(this.name)

    companion object {
        fun fromDomain(status: VersionState): VersionStateJpa =
            when (status) {
                VersionState.NEW -> throw IllegalStateException("NEW article state is invalid")
                else -> valueOf(status.name)
            }
    }
}