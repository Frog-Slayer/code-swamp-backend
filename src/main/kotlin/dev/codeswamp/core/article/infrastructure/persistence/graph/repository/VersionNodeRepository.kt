package dev.codeswamp.core.article.infrastructure.persistence.graph.repository

import dev.codeswamp.core.article.infrastructure.persistence.graph.node.VersionNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface VersionNodeRepository : Neo4jRepository<VersionNode, Long> {
    fun findByVersionId(versionId: Long): VersionNode?

    fun deleteAllByArticleId(articleId: Long)

    @Query( "MATCH (target: VersionNode{versionId: \$versionId}) SET target.isBase= true")
    fun markAsBase(versionId: Long)

    @Query("MATCH p= (base: VersionNode)-[:NEXT*0..]->(target: VersionNode{versionId: \$versionId})" +
            """WHERE base.isBase= true
            RETURN base
            ORDER BY length(p) ASC
            LIMIT 1 
        """)
    fun findBaseNodeNearestTo(versionId : Long) : VersionNode?

    @Query("MATCH p = shortestPath((n1: VersionNode{versionId: \$versionId1})-[:NEXT*0..]->(n2: VersionNode{versionId: \$versionId2})) RETURN p")
    fun findShortestPathBetween(versionId1: Long, versionId2: Long) : List<VersionNode>
}