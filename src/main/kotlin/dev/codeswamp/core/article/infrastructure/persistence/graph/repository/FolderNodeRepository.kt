package dev.codeswamp.core.article.infrastructure.persistence.graph.repository

import dev.codeswamp.core.article.infrastructure.persistence.graph.node.FolderNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface FolderNodeRepository : Neo4jRepository<FolderNode, Long> {

    @Query(
        "WITH \$rootName AS rootName, \$path AS pathNames" +
            """
            MATCH (root:Folder { name: rootName })
            
            CALL apoc.path.expandConfig(root, {
                relationshipFilter: "HAS_CHILD>",
                labelFilter: "Folder",
                maxLevel: size(pathNames),
                bfs: true
            }) YIELD path

            WITH path, nodes(path) AS n
            WHERE [i IN RANGE(1, size(pathNames)) | n[i].name] = pathNames
            RETURN n[-1].folderId
        """
    )
    fun findFolderIdByFullPath(rootName:String, path: List<String>): Long?

    @Query("MATCH (folder: Folder { folderId : \$folderId }) RETURN folder")
    fun findByFolderId(folderId: Long): FolderNode?


}