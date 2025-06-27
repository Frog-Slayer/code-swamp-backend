package dev.codeswamp.articleprojection.application.event.outbox.outbox

import dev.codeswamp.articleprojection.application.event.event.ArticlePublishedEvent
import dev.codeswamp.core.common.event.BusinessEvent
import org.springframework.stereotype.Component

@Component
class EventKeyResolver {
    fun resolveKey(event: BusinessEvent) : String {
        return when (event) {
            is ArticlePublishedEvent -> "article-${event.articleId} "
            else -> throw IllegalArgumentException("Unsupported event: $event")
        }
    }
}