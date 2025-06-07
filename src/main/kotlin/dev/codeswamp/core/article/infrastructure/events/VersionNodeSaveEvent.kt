package dev.codeswamp.core.article.infrastructure.events

data class VersionNodeSaveEvent (
    val versionId: Long,
    val articleId: Long,
    val isBase: Boolean,
    val previousNodeId: Long? ,
)