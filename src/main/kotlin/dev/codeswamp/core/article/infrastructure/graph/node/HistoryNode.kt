package dev.codeswamp.core.article.infrastructure.graph.node

import dev.codeswamp.core.article.domain.model.Article
import dev.codeswamp.core.article.domain.model.ArticleDiff
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node("History")
data class HistoryNode(
    @Id @GeneratedValue
    val id: Long? = null,

    val diffId: Long,

    @Relationship(type = "NEXT", direction = Relationship.Direction.INCOMING)
    val previous: HistoryNode? = null,

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    var next: List<HistoryNode> = emptyList()
) {
    companion object {
        fun fromDomain(diff: ArticleDiff, previous: HistoryNode?) = HistoryNode(
            diffId = diff.id!!,
            previous = previous
        )
    }
}