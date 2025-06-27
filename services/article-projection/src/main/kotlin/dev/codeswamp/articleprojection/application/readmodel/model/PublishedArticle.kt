package dev.codeswamp.articleprojection.application.readmodel.model

import java.time.Instant

data class PublishedArticle (
    val id: Long,
    val authorId: Long,
    val folderId: Long,

    val createdAt: Instant,
    val updatedAt: Instant,

    val summary: String,
    val thumbnail: String? = null,
    val isPublic: Boolean,

    val slug: String,
    val title: String,
    val content: String,
)
