package dev.codeswamp.article.infrastructure.persistence.graph.node

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Article")
data class ArticleNode(
    @Id @GeneratedValue val id: Long? = null,

    val articleId: Long,//PK of RDB entity

    @Relationship(type = "HAS_VERSION", direction = Relationship.Direction.OUTGOING)
    var currentVersion: VersionNode? = null
)