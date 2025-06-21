package dev.codeswamp.articlecommand.domain.folder.event

import dev.codeswamp.articlecommand.domain.DomainEvent
import java.time.Instant

data class FolderDeletionEvent(
    val rootId: Long,
    val descendantsIds: List<Long>,
    val deletedAt: Instant
) : DomainEvent
