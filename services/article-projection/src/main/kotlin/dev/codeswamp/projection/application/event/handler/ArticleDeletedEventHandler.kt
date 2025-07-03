package dev.codeswamp.projection.application.event.handler

import dev.codeswamp.projection.application.event.event.ArticleDeletedEvent
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.projection.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ArticleDeletedEventHandler(
    private val publishedArticleRepository: PublishedArticleRepository
): EventHandler<ArticleDeletedEvent> {
    override fun canHandle(event: Event): Boolean = event is  ArticleDeletedEvent

    @Transactional
    override suspend fun handle(event: ArticleDeletedEvent) {
        publishedArticleRepository.deleteByArticleId(event.articleId)
    }
}