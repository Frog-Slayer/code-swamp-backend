package dev.codeswamp.projection.application.event.handler

import dev.codeswamp.projection.application.event.event.PublishedArticleSavedEvent
import dev.codeswamp.projection.application.event.event.ArticlePublishedEvent
import dev.codeswamp.projection.application.readmodel.model.PublishedArticle
import dev.codeswamp.projection.application.readmodel.repository.PublishedArticleRepository
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.framework.application.outbox.EventRecorder
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
        val toPublish = event.toArticle()

        publishedArticleRepository.save(toPublish)

        eventRecorder.record(PublishedArticleSavedEvent(
            event.articleId,
        ))
    }

    fun ArticlePublishedEvent.toArticle() = PublishedArticle(
        id = articleId,
        authorId = authorId,
        folderId = folderId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        summary = summary,
        thumbnail = thumbnailUrl,
        isPublic = isPublic,
        slug = slug,
        title = title,
        content = fullContent
    )





}