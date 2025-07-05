package dev.codeswamp.projection.application.event.handler

import dev.codeswamp.projection.application.event.event.ArticleDeletedEvent
import dev.codeswamp.core.application.event.eventbus.EventHandler
import dev.codeswamp.core.common.event.Event
import dev.codeswamp.projection.application.event.event.FolderDeletedEvent
import dev.codeswamp.projection.application.readmodel.repository.PublishedArticleRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FolderDeletedEventHandler(
    private val publishedArticleRepository: PublishedArticleRepository
): EventHandler<FolderDeletedEvent> {
    override fun canHandle(event: Event): Boolean = event is FolderDeletedEvent

    @Transactional
    override suspend fun handle(event: FolderDeletedEvent) {
        val toDelete = event.descendantsIds + event.rootId
        publishedArticleRepository.deleteAllByFolderIds(toDelete)
    }
}