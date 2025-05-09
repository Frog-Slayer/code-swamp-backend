package dev.codeswamp.core.article.infrastructure.graph.service

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode

interface HistoryNodeService {
    fun remove(nodeId: Long)
    fun findLCA(nodeAId: Long, nodeBId: Long): HistoryNode?
    fun findPathBetweenNodes(nodeAId: Long, nodeBId: Long): List<HistoryNode>
}