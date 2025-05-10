package dev.codeswamp.core.article.infrastructure.graph.service

import dev.codeswamp.core.article.infrastructure.graph.repository.HistoryNodeRepository
import org.springframework.stereotype.Service

@Service
class HistoryNodeServiceImpl(
    private val historyNodeRepository: HistoryNodeRepository,
    ) : HistoryNodeService {

    override fun findLCA(diffId1: Long, diffId2: Long): Long {
        val node1 = historyNodeRepository.findByDiffId(diffId1) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(diffId2) ?: throw Exception("No node found")

        val lca = historyNodeRepository.findLCA(node1, node2) ?: throw Exception("No LCA found")

        return lca.diffId
    }

    override fun findPathBetweenNodes(
        diffId1: Long,
        diffId2: Long
    ): List<Long> {
        val node1 = historyNodeRepository.findByDiffId(diffId1) ?: throw Exception("No node found")
        val node2 = historyNodeRepository.findByDiffId(diffId2) ?: throw Exception("No node found")

        return historyNodeRepository.findPathBetween(node1, node2).map { it.diffId }
    }

}