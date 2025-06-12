package dev.codeswamp.core.article.domain.folder.event

import dev.codeswamp.core.article.domain.ArticleDomainEvent

data class FolderPathChangedEvent(
    val folderId: Long,
    val oldPath: String,
    val newPath: String,
) : ArticleDomainEvent