package dev.codeswamp.core.article.domain.folder.event

import dev.codeswamp.core.article.domain.ArticleDomainEvent
import java.time.Instant

data class FolderDeletionEvent (
    val rootId: Long,
    val descendantsIds: List<Long>,
    val deletedAt: Instant
) : ArticleDomainEvent
