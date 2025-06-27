package dev.codeswamp.articleprojection.application.event.handler

import dev.codeswamp.articleprojection.application.event.event.ArticleIndexingEvent
import dev.codeswamp.articleprojection.application.event.event.ArticlePublishedEvent
import dev.codeswamp.articleprojection.application.readmodel.model.PublishedArticle
import dev.codeswamp.articleprojection.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.application.event.eventbus.EventRecorder
import dev.codeswamp.core.common.event.Event
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticlePublishedEventHandler(
    private val publishedArticleRepository: PublishedArticleRepository,
    private val eventRecorder: EventRecorder,
): EventHandler<ArticlePublishedEvent> {
    override fun canHandle(event: Event): Boolean = event is ArticlePublishedEvent

    @Transactional
    override suspend fun handle(event: ArticlePublishedEvent) {
        publishedArticleRepository.save(PublishedArticle.of())

        eventRecorder.record(ArticleIndexingEvent(
            event.articleId,
        ))
    }
}