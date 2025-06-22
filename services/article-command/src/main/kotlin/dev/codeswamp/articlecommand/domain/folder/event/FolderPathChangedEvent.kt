package dev.codeswamp.articlecommand.domain.folder.event

import dev.codeswamp.core.domain.DomainEvent

data class FolderPathChangedEvent(
    val folderId: Long,
    val oldPath: String,
    val newPath: String,
) : DomainEvent