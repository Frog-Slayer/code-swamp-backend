package dev.codeswamp.articleprojection.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent

data class ArticleIndexingEvent(
    val articleId: Long,
): ApplicationEvent