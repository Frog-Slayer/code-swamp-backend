package dev.codeswamp.core.article.infrastructure.graph.node

import dev.codeswamp.core.article.domain.model.Article
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node("History")
data class HistoryNode(
    @Id @GeneratedValue val id: Long? = null,

    @Relationship(type = "NEXT", direction = Relationship.Direction.INCOMING)
    var previous: HistoryNode? = null,

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    var next: List<HistoryNode> = emptyList()
)