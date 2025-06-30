package dev.codeswamp.articlecommand.domain.folder.event

import dev.codeswamp.core.common.EventType
import dev.codeswamp.core.domain.DomainEvent

@EventType("folder.renamed")
data class FolderPathChangedEvent(
    val folderId: Long,
    val oldPath: String,
    val newPath: String,
) : DomainEvent