package dev.codeswamp.articlecommand.infrastructure.persistence.r2dbc.entity

import dev.codeswamp.articlecommand.domain.article.model.VersionedArticle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table(name = "article_metadata")
data class ArticleMetadataEntity(
    @Id
    val id: Long,

    @Column("folder_id")
    var folderId: Long,
    var slug: String?,

    @Column("author_id")
    var authorId: Long,

    @Column("is_public")
    var isPublic: Boolean,

    @Column("is_published")
    var isPublished: Boolean,

    @Column("created_at")
    var createdAt: Instant = Instant.now(),

    var summary: String = "",

    var thumbnail: String? = null,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle) = ArticleMetadataEntity(
            id = versionedArticle.id,
            authorId = versionedArticle.authorId,
            createdAt = versionedArticle.createdAt,
            isPublished = versionedArticle.hasBeenPublished,
            isPublic = versionedArticle.metadata.isPublic,
            folderId = versionedArticle.metadata.folderId,
            summary = versionedArticle.metadata.summary,
            slug = versionedArticle.metadata.slug?.value,
            thumbnail = versionedArticle.metadata.thumbnailUrl,
        )
    }

    fun updateTo(newMetadata: ArticleMetadataEntity) {
        folderId = newMetadata.folderId
        slug = newMetadata.slug
        isPublic = newMetadata.isPublic
        isPublished = newMetadata.isPublished
        summary = newMetadata.summary
        thumbnail = newMetadata.thumbnail
    }
}