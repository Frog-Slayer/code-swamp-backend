package dev.codeswamp.core.article.infrastructure.persistence.graph.repository

import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface VersionNodeRepository : Neo4jRepository<VersionNode, Long> {
    fun findByDiffId(diffId: Long): VersionNode?

    fun deleteAllByArticleId(articleId: Long)

    @Query(
        "MATCH path = (n1: HistoryNode {diffId: \$diffId1}) <-[:NEXT*0..]-(ancestor:HistoryNode)-[:NEXT*0..]->(n2: HistoryNode {diffId: \$diffId2}) " +
        """
        RETURN ancestor
        """
    )
    fun findLCA(diffId1: Long, diffId2: Long): VersionNode?

    @Query("MATCH p = shortestPath((n1: HistoryNode {diffId: \$diffId1})-[:NEXT*0..]->(n2: HistoryNode {diffId: \$diffId2})) RETURN p")
    fun findPathBetween(diffId1: Long, diffId2: Long): List<VersionNode>


    @Query("MATCH p= (snapshot:HistoryNode)-[:NEXT*0..]->(target:HistoryNode {diffId: \$diffId})" +
        """WHERE snapshot.isSnapshot = true
        RETURN snapshot
        ORDER BY length(p) ASC
        LIMIT 1 
    """)
    fun findNearestSnapshotBefore(diffId: Long): VersionNode
}