package dev.codeswamp.articlequery.application.readmodel.model

import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class PublishedArticle private constructor(
    val id: Long,
    val authorId: Long,
    val folderId: Long,
    val versionId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val summary: String,
    val thumbnailUrl: String? = null,
    val isPublic: Boolean,
    val slug: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(
            id: Long,
            versionId: Long,
            authorId: Long,
            folderId: Long,
            createdAt: Instant,
            updatedAt: Instant,
            summary: String,
            thumbnailUrl: String?,
            isPublic: Boolean,
            slug: String,
            title: String,
            content: String,
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

    fun assertReadableBy(userId: Long?) {
        if (!isPublic && (userId == null || authorId != userId))
            throw AccessDeniedException("cannot read private article")//TODO
    }
}