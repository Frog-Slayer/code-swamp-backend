package dev.codeswamp.core.article.infrastructure.graph.repository

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryNodeRepository : Neo4jRepository<HistoryNode, Long> {
    fun findByDiffId(diffId: Long): HistoryNode?

    fun findLCA(node1: HistoryNode, node2: HistoryNode): HistoryNode?
    fun findPathBetween(node1: HistoryNode, node2: HistoryNode): List<HistoryNode>
}