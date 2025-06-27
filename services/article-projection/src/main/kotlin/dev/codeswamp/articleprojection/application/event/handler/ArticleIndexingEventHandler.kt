package dev.codeswamp.articleprojection.application.event.handler

import dev.codeswamp.articleprojection.application.event.event.ArticleIndexingEvent
import dev.codeswamp.articleprojection.application.support.SearchEngineIndexer
import dev.codeswamp.articleprojection.application.support.MarkdownPreprocessor
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.application.event.eventbus.EventRecorder
import dev.codeswamp.core.common.event.Event
import org.springframework.stereotype.Component

@Component
class ArticleIndexingEventHandler(
    private val searchEngineIndexer: SearchEngineIndexer,
    private val markdownPreprocessor: MarkdownPreprocessor,
    private val eventRecorder: EventRecorder,
) : EventHandler<ArticleIndexingEvent> {
    override fun canHandle(event: Event): Boolean = event is ArticleIndexingEvent

    override suspend fun handle(event: ArticleIndexingEvent) {
        try {
            val indexDto = event.toDto()//확장 메서드로 구현
            searchEngineIndexer.index(indexDto)

        } catch ( e: Exception ){


        }
    }
}