package dev.codeswamp.core.article.domain.folder.event

import dev.codeswamp.core.article.domain.ArticleDomainEvent

data class FolderDeletedEvent (
    val folderIdsToDelete: List<Long>,
) : ArticleDomainEvent
