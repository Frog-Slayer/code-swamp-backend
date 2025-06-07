package dev.codeswamp.core.article.infrastructure.handler

import dev.codeswamp.core.article.infrastructure.events.VersionNodeSaveEvent
import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class VersionNodeSaveEventHandler(
    private val versionNodeRepository: VersionNodeRepository
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: VersionNodeSaveEvent) {
        val previousNode = versionNodeRepository.findByVersionId(event.versionId)

        val node = VersionNode(
            versionId = event.versionId,
            articleId = event.articleId,
            isBase = event.isBase,
            previous = previousNode,
        )

        versionNodeRepository.save(node)
    }
}