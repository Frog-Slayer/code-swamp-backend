package dev.codeswamp.projection.application.event.handler

import dev.codeswamp.projection.application.event.event.PublishedArticleSavedEvent
import dev.codeswamp.projection.application.support.SearchEngineIndexer
import dev.codeswamp.projection.application.support.MarkdownPreprocessor
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.framework.application.outbox.EventRecorder
import dev.codeswamp.projection.application.dto.command.IndexArticleCommand
import dev.codeswamp.projection.application.readmodel.model.PublishedArticle
import dev.codeswamp.projection.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Component

@Component
class PublishedArticleSavedEventHandler(
    private val searchEngineIndexer: SearchEngineIndexer,
    private val markdownPreprocessor: MarkdownPreprocessor,
    private val publishedArticleRepository: PublishedArticleRepository
) : EventHandler<PublishedArticleSavedEvent> {
    override fun canHandle(event: Event): Boolean = event is PublishedArticleSavedEvent

    override suspend fun handle(event: PublishedArticleSavedEvent) {
        try {
            val article  = publishedArticleRepository.findByArticleId(event.articleId)
                ?: throw IllegalArgumentException("No article with id ${event.articleId}")
            val preprocessed = markdownPreprocessor.preprocess(article.content)
            val indexCommand = IndexArticleCommand.from(article, preprocessed)
            searchEngineIndexer.index(indexCommand)
        } catch ( e: Exception ){


        }
    }
}