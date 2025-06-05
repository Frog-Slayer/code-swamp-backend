package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.ArticleState

enum class ArticleStatusJpa {
    ARCHIVED,
    DRAFT,
    PUBLISHED;

    fun toDomain(): ArticleState = ArticleState.valueOf(this.name)

    companion object {
        fun fromDomain(status: ArticleState): ArticleStatusJpa = valueOf(status.name)
    }
}