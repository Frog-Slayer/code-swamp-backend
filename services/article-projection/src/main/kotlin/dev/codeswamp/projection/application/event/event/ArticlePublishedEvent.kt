package dev.codeswamp.projection.application.event.event

import dev.codeswamp.core.application.event.ApplicationEvent
import dev.codeswamp.core.common.EventType
import java.time.Instant

@EventType("article.published")
data class ArticlePublishedEvent(
    val articleId: Long,
    val authorId: Long,
    val versionId: Long,

    val createdAt: Instant,
    val updatedAt: Instant,

    val folderId: Long,

    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,

    val slug: String,
    val title: String,
    val fullContent: String
) : ApplicationEvent



