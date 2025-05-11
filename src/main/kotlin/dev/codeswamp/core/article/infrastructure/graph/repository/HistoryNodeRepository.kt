package dev.codeswamp.core.article.infrastructure.graph.repository

import dev.codeswamp.core.article.infrastructure.graph.node.HistoryNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface HistoryNodeRepository : Neo4jRepository<HistoryNode, Long> {
    fun findByDiffId(diffId: Long): HistoryNode?

    @Query ("""
        MATCH (n:HistoryNode) 
        WHERE n.articleId = {articleId}
        DETACH DELETE n 
    """)
    fun deleteAllByArticleId(articleId: Long)

    @Query("""
        MATCH (n1:HistoryNode)-[:NEXT*]->(ancestor:HistoryNode)<-[:NEXT*]-(n2:HistoryNode)
        WHERE n1.id = {node1Id} AND n2.id = {node2Id}
        RETURN ancestor
        ORDER BY LENGTH((n1)-[:NEXT*]->(ancestor)) DESC
        LIMIT 1
    """)
    fun findLCA(node1Id: Long, node2Id: Long): HistoryNode?

    @Query("""
        MATCH p = shortestPath((n1:HistoryNode)-[:NEXT*]->(n2:HistoryNode))
        WHERE n1.id = {node1Id} AND n2.id = {node2Id}
        RETURN p
    """)
    fun findPathBetween(node1Id: Long, node2Id: Long): List<HistoryNode>


    @Query("""
        MATCH p= (snapshot:HistoryNode)-[:NEXT*]->(target:HistoryNode)
        WHERE target.id = {nodeId} AND snapshot.isSnapshot = true
        RETURN snapshot
        ORDER BY length(p) ASC
        LIMIT 1 
    """)
    fun findNearestSnapshotBefore(nodeId: Long): HistoryNode
}