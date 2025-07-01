package dev.codeswamp.projection.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.EventType

@EventType("article.published.saved")
data class PublishedArticleSavedEvent(
    val articleId: Long,
): ApplicationEvent