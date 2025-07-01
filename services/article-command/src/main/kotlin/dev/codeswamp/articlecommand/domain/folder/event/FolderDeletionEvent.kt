package dev.codeswamp.articlecommand.domain.folder.event

import dev.codeswamp.core.common.EventType
import dev.codeswamp.core.domain.DomainEvent
import java.time.Instant

@EventType("folder.deleted")
data class FolderDeletionEvent(
    val rootId: Long,
    val descendantsIds: List<Long>,
    val deletedAt: Instant
) : DomainEvent
