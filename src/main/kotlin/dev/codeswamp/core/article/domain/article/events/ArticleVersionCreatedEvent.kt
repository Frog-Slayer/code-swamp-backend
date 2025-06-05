package dev.codeswamp.core.article.domain.article.events

import dev.codeswamp.core.article.domain.ArticleDomainEvent

data class ArticleVersionCreatedEvent (
    val articleId: Long,
    val versionId: Long,
) : ArticleDomainEvent