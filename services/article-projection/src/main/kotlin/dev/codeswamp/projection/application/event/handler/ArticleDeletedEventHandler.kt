package dev.codeswamp.projection.application.event.handler

import dev.codeswamp.projection.application.event.event.ArticleDeletedEvent
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.common.event.Event

class ArticleDeletedEventHandler : EventHandler<ArticleDeletedEvent> {
    override fun canHandle(event: Event): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun handle(event: ArticleDeletedEvent) {
        TODO("Not yet implemented")
    }
}