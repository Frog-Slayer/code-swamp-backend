package dev.codeswamp.article.application.usecase.query.article.read

import java.time.Instant

data class ReadArticleResult(
    val id: Long,
    val authorId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val folderId: Long,
    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,
    val title: String,
    val content: String,
)