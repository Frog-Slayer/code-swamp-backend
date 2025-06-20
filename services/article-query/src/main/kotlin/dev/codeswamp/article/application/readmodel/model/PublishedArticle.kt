package dev.codeswamp.article.application.readmodel.model

import dev.codeswamp.article.domain.article.model.VersionedArticle
import org.springframework.security.access.AccessDeniedException
import java.time.Instant

data class PublishedArticle private constructor (
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
){
    companion object {
        fun of(versionedArticle: VersionedArticle, fullContent: String) = PublishedArticle (
            id = versionedArticle.id,
            authorId = versionedArticle.authorId,
            folderId = versionedArticle.metadata.folderId,

            createdAt = versionedArticle.createdAt,
            updateAt = versionedArticle.currentVersion.createdAt,

            summary = versionedArticle.metadata.summary,
            thumbnailUrl = versionedArticle.metadata.thumbnailUrl,
            isPublic = versionedArticle.metadata.isPublic,

            slug = requireNotNull(versionedArticle.metadata.slug).value,
            title = requireNotNull(versionedArticle.currentVersion.title).value,

            content = fullContent,
        )

        fun from(
            id: Long,
            authorId: Long,
            folderId: Long,
            createdAt: Instant,
            updateAt: Instant,
            summary: String,
            thumbnailUrl: String?,
            isPublic : Boolean,
            slug: String,
            title: String,
            content: String,
        ) = PublishedArticle (
            id = id,
            authorId = authorId,
            folderId = folderId,
            createdAt = createdAt,
            updateAt = updateAt,
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