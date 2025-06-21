package dev.codeswamp.article.infrastructure.persistence.jpa.entity

import dev.codeswamp.article.domain.article.model.VersionedArticle
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name = "article",
    uniqueConstraints = [UniqueConstraint(columnNames = ["folder_id", "slug"])]
)
data class ArticleMetadataEntity(
//ArticleMetadata
    @Id//도메인에서 생성
    val id: Long,

    var title: String?,

    @Column(nullable = false, name = "folder_id")
    var folderId: Long,

    var slug: String?,

    @Column(nullable = false)
    var authorId: Long,

    var isPublic: Boolean,
    var isPublished: Boolean,

    @Column(columnDefinition = "TIMESTAMP(3)")
    var createdAt: Instant = Instant.now(),

    var summary: String = "",

    var thumbnailUrl: String? = null,
) {
    companion object {
        fun from(versionedArticle: VersionedArticle) = ArticleMetadataEntity(
            id = versionedArticle.id,
            authorId = versionedArticle.authorId,
            createdAt = versionedArticle.createdAt,
            isPublished = versionedArticle.isPublished,
            title = versionedArticle.currentVersion.title?.value,
            isPublic = versionedArticle.metadata.isPublic,
            folderId = versionedArticle.metadata.folderId,
            summary = versionedArticle.metadata.summary,
            slug = versionedArticle.metadata.slug?.value,
            thumbnailUrl = versionedArticle.metadata.thumbnailUrl,
        )
    }

    fun updateTo(newMetadata: ArticleMetadataEntity) {
        title = newMetadata.title
        folderId = newMetadata.folderId
        slug = newMetadata.slug
        isPublic = newMetadata.isPublic
        isPublished = newMetadata.isPublished
        summary = newMetadata.summary
        thumbnailUrl = newMetadata.thumbnailUrl
    }
}