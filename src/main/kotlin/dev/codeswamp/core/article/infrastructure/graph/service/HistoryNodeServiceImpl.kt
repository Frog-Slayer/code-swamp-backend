package dev.codeswamp.core.article.infrastructure.graph.service

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode

class HistoryNodeServiceImpl : HistoryNodeService {
    override fun remove(nodeId: Long) {
        TODO("Not yet implemented")
    }

    override fun findLCA(nodeAId: Long, nodeBId: Long): HistoryNode? {
        TODO("Not yet implemented")
    }

    override fun findPathBetweenNodes(
        nodeAId: Long,
        nodeBId: Long
    ): List<HistoryNode> {
        TODO("Not yet implemented")
    }
}