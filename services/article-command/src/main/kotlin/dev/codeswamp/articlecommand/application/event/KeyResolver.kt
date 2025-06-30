package dev.codeswamp.articlecommand.application.event

import dev.codeswamp.articlecommand.domain.article.event.ArticleDeletedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleDraftedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticlePublishedEvent
import dev.codeswamp.articlecommand.domain.article.event.ArticleVersionCreatedEvent
import dev.codeswamp.core.application.event.eventbus.EventKeyResolver
import dev.codeswamp.core.common.event.BusinessEvent
import org.springframework.stereotype.Component

@Component
class KeyResolver : EventKeyResolver{
    override fun resolveKey(event: BusinessEvent): String {
        return when (event) {
            is ArticleDeletedEvent -> "article-deleted-${event.articleId}"
            is ArticleDraftedEvent -> "article-drafted-${event.articleId}-${event.versionId}"
            is ArticlePublishedEvent -> "article-published-${event.articleId}-${event.versionId}"
            is ArticleVersionCreatedEvent -> "article-version-created-${event.versionId}"
            else -> throw IllegalArgumentException("Unsupported BusinessEvent: $event")
        }
    }
}