package dev.codeswamp.articlequery.infrastructure.persistence.r2dbc.dto

import java.time.Instant

data class ArticleListItemProjection(
    val id: Long,
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