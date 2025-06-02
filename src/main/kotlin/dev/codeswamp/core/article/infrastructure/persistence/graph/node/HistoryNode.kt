package dev.codeswamp.core.article.infrastructure.persistence.graph.node

import dev.codeswamp.core.article.domain.article.model.ArticleDiff
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node("HistoryNode")
data class HistoryNode(
    @Id @GeneratedValue
    val id: Long? = null,

    val articleId: Long,

    val diffId: Long,

    val isSnapshot: Boolean = false,

    @Relationship(type = "NEXT", direction = Relationship.Direction.INCOMING)
    val previous: HistoryNode? = null,

) {
    companion object {
        fun fromDomain(diff: ArticleDiff, previous: HistoryNode?) = HistoryNode(
            articleId = diff.articleId,
            diffId = diff.id!!,
            previous = previous
        )
    }
}