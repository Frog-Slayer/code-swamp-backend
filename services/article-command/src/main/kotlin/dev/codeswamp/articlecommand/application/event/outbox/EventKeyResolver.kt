package dev.codeswamp.articlecommand.application.event.outbox

import dev.codeswamp.articlecommand.domain.article.event.ArticleDraftedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.articlecommand.domain.folder.event.FolderDeletionEvent
import dev.codeswamp.articlecommand.domain.folder.event.FolderPathChangedEvent
import dev.codeswamp.core.common.event.BusinessEvent
import org.springframework.stereotype.Component

@Component
class EventKeyResolver {
    fun resolveKey(event: BusinessEvent) : String {
        return when (event) {
            is ArticleDraftedEvent -> "article-${event.articleId}"
            is ArticlePublishedEvent -> "article-${event.articleId} "
            is ArticleVersionCreatedEvent ->  "article-${event.articleId}"
            is FolderDeletionEvent -> "folder-${event.rootId}"
            is FolderPathChangedEvent -> "folder-${event.folderId}"
            else -> throw IllegalArgumentException("Unsupported event: $event")
        }
    }
}