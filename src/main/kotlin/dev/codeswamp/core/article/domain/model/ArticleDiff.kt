package dev.codeswamp.core.article.domain.model

import java.time.Instant

data class ArticleDiff (
    val id: Long? = null,
    val articleId: Long,
    val previousVersionId: Long? = null,
    val diffData: String,
    val createdAt: Instant = Instant.now()
)
