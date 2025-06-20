package dev.codeswamp.article.application.event.handler

import dev.codeswamp.article.domain.folder.event.FolderPathChangedEvent
import dev.codeswamp.article.domain.folder.repository.FolderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FolderPathChangedEventHandler (
    private val folderRepository : FolderRepository
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)//TODO: Race Condition 있음
    fun handle(event: FolderPathChangedEvent) {

        folderRepository.updateDescendentsFullPath(event.oldPath, event.newPath)

    }
}
