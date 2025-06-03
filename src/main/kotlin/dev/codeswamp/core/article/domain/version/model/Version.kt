package dev.codeswamp.core.article.domain.version.model

import java.time.Instant

data class Version (
    val id: Long? = null,
    val articleId: Long,
    val previousVersionId: Long? = null,
    val diff: String,
    val createdAt: Instant = Instant.now(),

    val isSnapshot: Boolean = false,
    val snapshotContent: String? = null,
)