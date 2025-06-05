package dev.codeswamp.core.article.infrastructure.persistence.graph.repository

import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface VersionNodeRepository : Neo4jRepository<VersionNode, Long> {
    fun findByDiffId(diffId: Long): VersionNode?

    fun deleteAllByArticleId(articleId: Long)

    @Query("MATCH p = (snapshot: VersionNode) -[:NEXT*0..]-> (target:HistoryNode{versionId : \$versionId})" +
        """WHERE snapshot.isSnapshot = true
        RETURN p
        ORDER BY length(p) ASC
        LIMIT 1 
    """)
    fun findDiffChainFromNearestSnapshot(versionId: Long): List<VersionNode>
}