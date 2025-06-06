package dev.codeswamp.core.article.application.usecase.query.read

import java.time.Instant

data class ReadArticleResult(
    val id: Long,
    val authorId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val folderId: Long,
    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic : Boolean,
    val title: String,
    val content: String,
)