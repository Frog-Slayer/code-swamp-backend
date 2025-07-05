package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.dto

import java.time.Instant

data class ArticleSummaryDto(
    val id: Long,
    val versionId: Long,
    val authorId: Long,
    val folderId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val summary: String,
    val thumbnail: String?,
    val isPublic: Boolean,
    val slug: String,
    val title: String,
)