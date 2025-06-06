package dev.codeswamp.core.article.infrastructure.persistence.graph.node

import dev.codeswamp.core.article.domain.folder.model.Folder
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Folder")
data class FolderNode (
    @Id @GeneratedValue
    var id: Long? = null,

    val folderId: Long,
    val name: String,

    @Relationship(value = "HAS_CHILD", direction = Relationship.Direction.INCOMING)
    val parent: FolderNode? = null,
) {
    companion object {
        fun from(folder: Folder, parent: FolderNode?): FolderNode =  FolderNode(
            folderId = folder.id!!,
            name = folder.name,
            parent = parent
        )
    }
}