package dev.codeswamp.articlequery.application.usecase.query.article.list

import java.time.Instant

data class ArticleListItem (
    val id: Long,

    val authorId: Long,
    val authorName: String,
    val authorNickname: String,
    val authorProfileImage: String,

    val folderPath: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val summary: String,
    val thumbnailUrl: String?,
    val isPublic: Boolean,
    val slug: String,
    val title: String,
)