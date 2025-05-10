package dev.codeswamp.core.article.infrastructure.graph.service

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode
import dev.codeswamp.core.article.infrastructure.graph.repository.HistoryNodeRepository
import org.springframework.stereotype.Service

@Service
class HistoryNodeServiceImpl(
    private val historyNodeRepository: HistoryNodeRepository,
    ) : HistoryNodeService {
    override fun addNode(node: HistoryNode) {
        historyNodeRepository.save(node)
    }

    override fun findLCA(diffId1: Long, diffId2: Long): Long {
        val node1 = historyNodeRepository.findByDiffId(diffId1) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(diffId2) ?: throw Exception("No node found")

        val lca = historyNodeRepository.findLCA(node1.id ?: throw IllegalArgumentException("node1 ID is null"),
            node2.id ?: throw IllegalArgumentException("node2 ID is null"))
            ?: throw IllegalArgumentException("No LCA found between nodes with diffId $diffId1 and $diffId2")

        return lca.diffId
    }

    override fun findPathBetweenNodes(
        diffId1: Long,
        diffId2: Long
    ): List<Long> {
        val node1 = historyNodeRepository.findByDiffId(diffId1) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(diffId2) ?: throw Exception("No node found")

        return historyNodeRepository.findPathBetween(node1.id ?: throw IllegalArgumentException("node1 ID is null"),
            node2.id ?: throw IllegalArgumentException("node2 ID is null")).map{ it.diffId }
    }

    override fun findNearestSnapshotBefore(diffId: Long): Long {
        val node = historyNodeRepository.findByDiffId(diffId) ?: throw Exception("No node found")

        return historyNodeRepository.findNearestSnapshotBefore(node.id?: throw Exception("no such")).diffId

    }

}