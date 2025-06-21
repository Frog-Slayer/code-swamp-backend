package dev.codeswamp.articlecommand.infrastructure.event.event

data class VersionNodeSaveEvent(
    val versionId: Long,
    val articleId: Long,
    val isBase: Boolean,
    val previousNodeId: Long?,
) : InfraEvent