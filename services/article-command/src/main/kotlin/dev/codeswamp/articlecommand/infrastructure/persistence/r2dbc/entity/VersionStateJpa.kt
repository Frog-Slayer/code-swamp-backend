package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlecommand.domain.article.model.VersionState

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