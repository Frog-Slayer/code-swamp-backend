package dev.codeswamp.article.domain.folder.event

import dev.codeswamp.article.domain.ArticleDomainEvent

data class FolderPathChangedEvent(
    val folderId: Long,
    val oldPath: String,
    val newPath: String,
) : ArticleDomainEvent