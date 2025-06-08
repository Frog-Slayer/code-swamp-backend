package dev.codeswamp.core.article.infrastructure.event.event

data class VersionNodeSaveEvent (
    val versionId: Long,
    val articleId: Long,
    val isBase: Boolean,
    val previousNodeId: Long? ,
)