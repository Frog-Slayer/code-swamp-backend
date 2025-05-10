package dev.codeswamp.core.article.infrastructure.graph.service

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode

interface HistoryNodeService {
    fun addNode(node: HistoryNode)
    fun findLCA(diffId1: Long, diffId2: Long): Long
    fun findPathBetweenNodes(diffId1: Long, diffId2: Long): List<Long>
    fun findNearestSnapshotBefore(diffId: Long): Long
}