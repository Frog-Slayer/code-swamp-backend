package dev.codeswamp.core.article.infrastructure.persistence.graph.node

import dev.codeswamp.core.article.domain.version.model.Version
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship


@Node("VersionNode")
data class VersionNode(
    @Id @GeneratedValue
    val id: Long? = null,

    val articleId: Long,

    val diffId: Long,

    val isSnapshot: Boolean = false,

    @Relationship(type = "NEXT", direction = Relationship.Direction.INCOMING)
    val previous: VersionNode? = null,

    ) {
    companion object {
        fun fromDomain(diff: Version, previous: VersionNode?) = VersionNode(
            articleId = diff.articleId,
            diffId = diff.id!!,
            previous = previous
        )
    }
}