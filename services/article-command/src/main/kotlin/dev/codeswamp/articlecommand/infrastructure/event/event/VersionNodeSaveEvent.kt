package dev.codeswamp.articlecommand.infrastructure.event.event

import dev.codeswamp.core.infrastructure.event.InfraEvent

data class VersionNodeSaveEvent(
    val versionId: Long,
    val articleId: Long,
    val isBase: Boolean,
    val previousNodeId: Long?,
) : InfraEvent