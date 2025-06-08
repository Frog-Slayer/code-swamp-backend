package dev.codeswamp.core.article.infrastructure.persistence.graph.repository

import dev.codeswamp.core.article.infrastructure.persistence.graph.node.FolderNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface FolderNodeRepository : Neo4jRepository<FolderNode, Long> {

    @Query(
        "WITH \$path AS pathNames MATCH (root:Folder { name: pathNames[0] })" +
                """
                CALL apoc.path.expandConfig(root, {
                    relationshipFilter: "HAS_CHILD>",
                    labelFilter: "Folder",
                    maxLevel: size(pathNames) - 1,
                    bfs: true
                }) YIELD path

                WITH path, nodes(path) AS n, pathNames
                WHERE [i IN RANGE(0, size(pathNames) - 1) | n[i].name] = pathNames
                RETURN n[-1].folderId
                LIMIT 1
            """
    )
    fun findFolderIdByFullPath(path: List<String>): Long?

    @Query("MATCH (folder: Folder { folderId : \$folderId }) RETURN folder")
    fun findByFolderId(folderId: Long): FolderNode?


}