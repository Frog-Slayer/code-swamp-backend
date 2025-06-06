package dev.codeswamp.core.article.application.readmodel.model

import java.time.Instant

data class PublishedArticle (
    val id: Long,//versionedArticle에서 사용하는 id와 동일
    val authorId: Long,
    val folderId: Long,

    val createdAt: Instant,
    val updateAt: Instant,

    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic : Boolean,

    val slug: String,
    val title: String,
    val content: String,
)