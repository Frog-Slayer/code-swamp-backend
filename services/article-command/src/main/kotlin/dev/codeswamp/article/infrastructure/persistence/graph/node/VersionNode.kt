package dev.codeswamp.article.infrastructure.persistence.graph.node

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node("VersionNode")
data class VersionNode(
    @Id @GeneratedValue
    val id: Long? = null,

    val articleId: Long,

    val versionId: Long,

    val isBase: Boolean = false,

    @Relationship(type = "NEXT", direction = Relationship.Direction.INCOMING)
    val previous: VersionNode? = null,
)