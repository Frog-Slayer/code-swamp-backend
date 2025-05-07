package dev.codeswamp.core.article.infrastructure.graph.node

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node("Article")
data class ArticleNode (
    @Id @GeneratedValue val id: Long? = null,

    val articleId: Long,//PK of RDB entity
)