package dev.codeswamp.articlequery.application.readmodel.model

import java.time.Instant

data class PublishedArticle private constructor(
    val id: Long?,
    val authorId: Long?,
    val folderId: Long?,
    val versionId: Long?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val summary: String?,
    val thumbnailUrl: String?,
    val isPublic: Boolean?,
    val slug: String?,
    val title: String?,
    val content: String?,
) {
    companion object {
        fun from(
            id: Long? = null,
            versionId: Long? = null,
            authorId: Long? = null,
            folderId: Long? = null,
            createdAt: Instant? = null,
            updatedAt: Instant? = null,
            summary: String? = null,
            thumbnailUrl: String? = null,
            isPublic: Boolean? = null,
            slug: String? = null,
            title: String? = null,
            content: String? = null,
        ) = PublishedArticle(
            id = id,
            versionId = versionId,
            authorId = authorId,
            folderId = folderId,
            createdAt = createdAt,
            updatedAt = updatedAt,
            summary = summary,
            thumbnailUrl = thumbnailUrl,
            isPublic = isPublic,
            slug = slug,
            title = title,
            content = content
        )
    }
}