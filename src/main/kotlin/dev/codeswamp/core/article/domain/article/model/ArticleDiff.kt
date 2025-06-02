package dev.codeswamp.core.article.domain.article.model

import java.time.Instant

data class ArticleDiff (
    val id: Long? = null,
    val articleId: Long,
    val previousVersionId: Long? = null,
    val diffData: String,
    val createdAt: Instant = Instant.now(),

    val isSnapshot: Boolean = false,
    val snapshotContent: String? = null,
)
