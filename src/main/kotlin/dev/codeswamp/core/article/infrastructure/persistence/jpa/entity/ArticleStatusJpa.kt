package dev.codeswamp.core.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.core.article.domain.article.model.ArticleStatus

enum class ArticleStatusJpa {
    ARCHIVED,
    DRAFT,
    PUBLISHED;

    fun toDomain(): ArticleStatus = ArticleStatus.valueOf(this.name)

    companion object {
        fun fromDomain(status: ArticleStatus): ArticleStatusJpa = valueOf(status.name)
    }
}