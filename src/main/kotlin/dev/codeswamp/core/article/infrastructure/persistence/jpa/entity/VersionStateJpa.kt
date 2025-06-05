package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.VersionState

enum class VersionStatusJpa {
    ARCHIVED,
    DRAFT,
    PUBLISHED;

    fun toDomain(): VersionState = VersionState.valueOf(this.name)

    companion object {
        fun fromDomain(status: VersionState): VersionStatusJpa =
            when (status) {
                VersionState.NEW -> throw IllegalStateException("NEW article state is invalid")
                else -> valueOf(status.name)
            }
    }
}