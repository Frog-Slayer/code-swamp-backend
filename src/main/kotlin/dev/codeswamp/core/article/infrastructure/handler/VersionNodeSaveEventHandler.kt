package dev.codeswamp.core.article.infrastructure.handler

import dev.codeswamp.core.article.infrastructure.events.VersionNodeSaveEvent
import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import dev.codeswamp.core.article.infrastructure.persistence.graph.repository.VersionNodeRepository
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class VersionNodeSaveEventHandler(
    private val versionNodeRepository: VersionNodeRepository
) {

    //@EventListener -> For Test Only
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: VersionNodeSaveEvent) {
        val previousNode = event.previousNodeId?.let { versionNodeRepository.findByVersionId(event.previousNodeId) }

        val node = VersionNode(
            versionId = event.versionId,
            articleId = event.articleId,
            isBase = event.isBase,
            previous = previousNode,
        )

        versionNodeRepository.save(node)
    }
}