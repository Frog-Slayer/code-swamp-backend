package dev.codeswamp.domain.folder.entity.node

import dev.codeswamp.domain.article.infrastructure.graph.node.ArticleNode
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Folder")
data class FolderNode (
    @Id @GeneratedValue
    var id: Long? = null,

    val folderId: Long,

    @Relationship(value = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    val articles: List<ArticleNode> = mutableListOf(),

    @Relationship(value = "HAS_CHILD", direction = Relationship.Direction.OUTGOING)
    val children: List<FolderNode> = mutableListOf(),

    @Relationship(value = "HAS_CHILD", direction = Relationship.Direction.INCOMING)
    val parent: FolderNode? = null,
)