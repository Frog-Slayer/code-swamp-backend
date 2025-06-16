package dev.codeswamp.core.article.application.event.handler

import dev.codeswamp.core.article.application.cache.FolderDeletionCache
import dev.codeswamp.core.article.domain.article.repository.ArticleRepository
import dev.codeswamp.core.article.domain.folder.event.FolderDeletionEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FolderDeletionEventHandler (
    private val articleRepository: ArticleRepository,
    private val deletionCache: FolderDeletionCache
) {

    //TODO 실패 시 retry 필요
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)//TODO: Race Condition 있음
    fun handle(event: FolderDeletionEvent) {
        val foldersToDelete = event.descendantsIds + event.rootId
        articleRepository.deleteAllByFolderIdIn( foldersToDelete)
        deletionCache.removeDeletedMark(foldersToDelete)
    }
}